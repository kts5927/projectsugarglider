package com.projectsugarglider.kca.db;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.projectsugarglider.util.service.DateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceInfoPartitionManager {
    
    private final DateTime dateTime;
    private final JdbcTemplate jdbcTemplate;
    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;
    private static final Pattern PARTITION_NAME = Pattern.compile("^kca_price_info_(\\d{8})$");



    @EventListener(ApplicationReadyEvent.class)
    public void createWeeklyPartitions() {
        createPartitions();
        dropPastPartitions();
    }


    private void createPartitions(){

        String ymd = dateTime.previousWeekFridayYyyyMmDd();

        String sql = """
            CREATE TABLE IF NOT EXISTS kca_price_info_%s
            PARTITION OF kca_price_info
            FOR VALUES IN ('%s')
            """.formatted(ymd,ymd);

        jdbcTemplate.execute(sql);

    }

private void dropPastPartitions() {
    // 최근 4주만 남기기 (KST 기준)
    LocalDate cutoff = LocalDate.now(ZoneId.of("Asia/Seoul")).minusWeeks(4);

    // 부모 파티션 테이블 이름은 'kca_price_info' (언더스코어 없음)
    String sql = """
        SELECT c.relname
        FROM pg_class c
        JOIN pg_inherits i ON i.inhrelid = c.oid
        JOIN pg_class p ON p.oid = i.inhparent
        WHERE p.relname = 'kca_price_info'
    """;

    List<String> partitions = jdbcTemplate.queryForList(sql, String.class);

    for (String relname : partitions) {
        Matcher m = PARTITION_NAME.matcher(relname);
        if (!m.matches()) continue; 

        String yyyymmdd = m.group(1);
        LocalDate partDate;
        try {
            partDate = LocalDate.parse(yyyymmdd, BASIC);
        } catch (DateTimeParseException e) {
            continue; 
        }

        if (partDate.isBefore(cutoff)) {
            String drop = "DROP TABLE IF EXISTS " + relname;
            log.info("▼ 오래된 파티션 드랍: {} (날짜:{})", relname, partDate);
            jdbcTemplate.execute(drop);
        }
    }
}
}
