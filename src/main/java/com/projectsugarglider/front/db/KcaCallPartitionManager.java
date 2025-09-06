package com.projectsugarglider.front.db;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
public class KcaCallPartitionManager{

    private final DateTime dateTime;
    private final JdbcTemplate jdbcTemplate;
    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;


    @EventListener(ApplicationReadyEvent.class)
    public void createWeeklyPartitions() {
        createPartitions();
        dropPastPartitions();
    }


    private void createPartitions(){

        String ymd = dateTime.kstNowYYYYMMDD();

        String sql = """
            CREATE TABLE IF NOT EXISTS kca_call_history_%s
            PARTITION OF kca_call_history
            FOR VALUES IN ('%s')
            """.formatted(ymd,ymd);

        jdbcTemplate.execute(sql);

    }

private void dropPastPartitions() {
    String sql = """
        SELECT c.relname
        FROM pg_class c
        JOIN pg_inherits i ON i.inhrelid = c.oid
        JOIN pg_class p ON p.oid = i.inhparent
        WHERE p.relname = 'kca_call_history'
    """;


    List<String> partitions = jdbcTemplate.queryForList(sql, String.class);
    LocalDate today = LocalDate.now();
    for (String relname : partitions) {
        String prefix = "kca_call_history_";
        String yyyymmdd = relname.substring(prefix.length());
        LocalDate partDate = LocalDate.parse(yyyymmdd, BASIC);
        if (partDate.isBefore(today)) {
            String drop = "DROP TABLE IF EXISTS " + relname;
            log.info("▼ 오래된 파티션 드랍: {} (날짜:{})", relname, partDate);
            jdbcTemplate.execute(drop);
        }

    }
}
}