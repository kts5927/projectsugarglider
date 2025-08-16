package com.projectsugarglider.weather.db;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WeatherPartitionManager {

    private static final DateTimeFormatter Y4 = DateTimeFormatter.ofPattern("yyyy");
    private static final DateTimeFormatter M2 = DateTimeFormatter.ofPattern("MM");
    private static final DateTimeFormatter D2 = DateTimeFormatter.ofPattern("dd");
    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;

    private final JdbcTemplate jdbcTemplate;

    public WeatherPartitionManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createWeeklyPartitions() {
        LocalDate start = LocalDate.now();
        for (int i = 0; i < 6; i++) {
            createDailyPartition(start.plusDays(i));
        }

        dropPastPartitions();
    }

    private void createDailyPartition(LocalDate date) {
        String y = date.format(Y4);
        String m = date.format(M2);
        String d = date.format(D2);

        String partitionName = String.format("short_forecast_data_%s%s%s", y, m, d);

        String from = String.format("('%s','%s','%s')", y, m, d);
        LocalDate next = date.plusDays(1);
        String to = String.format("('%s','%s','%s')",
                                  next.format(Y4),
                                  next.format(M2),
                                  next.format(D2));

        String sql = """
            CREATE TABLE IF NOT EXISTS %s
              PARTITION OF short_forecast_data
              FOR VALUES FROM %s TO %s
            """.formatted(partitionName, from, to);

        log.info("▶ Weather daily 파티션 생성 SQL: {}", sql);
        jdbcTemplate.execute(sql);
    }

    public void dropPastPartitions() {
        LocalDate today = LocalDate.now();

        String sql = """
            SELECT c.relname
            FROM pg_class c
            JOIN pg_inherits i ON i.inhrelid = c.oid
            JOIN pg_class p ON p.oid = i.inhparent
            WHERE p.relname = 'short_forecast_data'
        """;

        List<String> partitions = jdbcTemplate.queryForList(sql, String.class);

        for (String relname : partitions) {

            // 네이밍 규칙 short_forecast_data_yyyyMMdd 만 대상
            String prefix = "short_forecast_data_";
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

