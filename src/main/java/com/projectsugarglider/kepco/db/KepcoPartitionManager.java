package com.projectsugarglider.kepco.db;



import java.time.YearMonth;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KepcoPartitionManager {

    private final JdbcTemplate jdbcTemplate;

    public KepcoPartitionManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** 앱 시작 시 당월 파티션 생성 */
    @EventListener(ApplicationReadyEvent.class)
    public void createCurrentMonthPartition() {
        YearMonth now = YearMonth.now().minusYears(1);
        createPartition(now);
    }

    /** 매월 1일 자정에 다음 달 파티션 생성 */
    @Scheduled(cron = "0 0 0 1 * *")
    public void createNextMonthPartition() {
        YearMonth next = YearMonth.now().minusYears(1).plusMonths(1);
        createPartition(next);
    }

    /** 연·월 기준 파티션 생성 로직 (IF NOT EXISTS) */
    private void createPartition(YearMonth ym) {
        String partName = String.format("kepco_usage_%04d%02d",
                                        ym.getYear(), ym.getMonthValue());
        String from     = String.format("('%04d','%02d')",
                                        ym.getYear(), ym.getMonthValue());
        YearMonth next  = ym.plusMonths(1);
        String to       = String.format("('%04d','%02d')",
                                        next.getYear(), next.getMonthValue());

        String sql = """
            CREATE TABLE IF NOT EXISTS %s
              PARTITION OF kepco_usage
              FOR VALUES FROM %s TO %s
            """.formatted(partName, from, to);

        jdbcTemplate.execute(sql);
    }
}