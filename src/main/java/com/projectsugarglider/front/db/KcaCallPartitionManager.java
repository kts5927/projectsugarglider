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

    /**
     * 서버가 부팅될때 자동으로 파티션의 생성/드랍을 하는 로직입니다.
     * 애플리케이션이 완전히 기동되고 요청을 받을 준비가 된 시점에
     * 한번 발동되는 이벤트를 구독합니다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void createWeeklyPartitions() {
        createPartitions();
        dropPastPartitions();
    }

    /**
     * 파티션을 만듭니다.
     * 오늘 날짜의 파티션이 없다면 만들고, 
     * 있다면 만들지 않습니다.
     */
    private void createPartitions(){

        String ymd = dateTime.kstNowYYYYMMDD();

        String sql = """
            CREATE TABLE IF NOT EXISTS kca_call_history_%s
            PARTITION OF kca_call_history
            FOR VALUES IN ('%s')
            """.formatted(ymd,ymd);

        jdbcTemplate.execute(sql);

    }
/**
 * 오래된 파티션을 드랍하는 로직입니다.
 * DB에서 파티션 리스트를 가져와서
 * 각 파티션의 이름을 날짜만 가져온 뒤
 * 특정 날짜 이전의 파티션을 드랍하는 형식입니다.
 * 
 * 이 로직은 데이터가 호출되었는지 확인하는 캐시의 역할을 하기 때문에
 * 오늘의 데이터만 남기고, 이전의 데이터를 모두 드랍합니다.
 * 
 * 만약 추후 데이터의 보관이 필요하다면
 * 다른 DB로 옮겨서 저장하는 등의 추가적인 조취가 필요합니다.
 */
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