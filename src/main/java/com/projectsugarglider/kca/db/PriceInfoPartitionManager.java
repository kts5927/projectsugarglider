package com.projectsugarglider.kca.db;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.projectsugarglider.util.service.DateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//TODO : 파티셔닝 자동화 메서드 만들기
/**
 * KCA(소비자원) 상품 가격정보 DB 파티셔닝 매니저.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PriceInfoPartitionManager {
    
    private final DateTime dateTime;
    private final JdbcTemplate jdbcTemplate;
    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;

    /**
     * 서버가 부팅될때 자동으로 파티션의 생성/드랍을 하는 로직입니다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void createWeeklyPartitions() {
        createPartitions();
        dropPastPartitions();
    }

    /**
     * 2주전 금요일을 기준으로 파티션이 만들어집니다.
     * 만약 해당날짜의 파티션이 있다면 만들지 않습니다.
     */
    private void createPartitions(){

        String ymd = dateTime.previousTwoWeekFridayYyyyMmDd();

        String sql = """
            CREATE TABLE IF NOT EXISTS kca_price_info_%s
            PARTITION OF kca_price_info
            FOR VALUES IN ('%s')
            """.formatted(ymd,ymd);

        jdbcTemplate.execute(sql);

    }
/**
 * 오래된 파티션을 드랍하는 로직입니다.
 * 오늘 날짜 기준으로 4주 이전 파티션을 드랍합니다.
 * 파티션명 : "kca_price_info_yyyyMMdd", 기준 : TZ=Asia/Seoul.
 *
 * 만약 추후 데이터의 보관이 필요하다면
 * 다른 DB로 옮겨서 저장하는 등의 추가적인 조취가 필요합니다.
 */
private void dropPastPartitions() {
    LocalDate cutoff = LocalDate.now(ZoneId.of("Asia/Seoul")).minusWeeks(4);

    String sql = """
        SELECT c.relname
        FROM pg_class c
        JOIN pg_inherits i ON i.inhrelid = c.oid
        JOIN pg_class p ON p.oid = i.inhparent
        WHERE p.relname = 'kca_price_info'
    """;

    List<String> partitions = jdbcTemplate.queryForList(sql, String.class);
    for (String relname : partitions) {
        String prefix = "kca_price_info_";
        String yyyymmdd = relname.substring(prefix.length());
        LocalDate partDate = LocalDate.parse(yyyymmdd, BASIC);
        if (partDate.isBefore(cutoff)) {
            String drop = "DROP TABLE IF EXISTS " + relname;
            log.info("▼ 오래된 파티션 드랍: {} (날짜:{})", relname, partDate);
            jdbcTemplate.execute(drop);
        }

    }
}
}
