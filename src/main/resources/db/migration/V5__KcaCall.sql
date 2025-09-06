CREATE TABLE kca_call_history (
    kca_call_day    CHAR(8),
    entp_id          VARCHAR(12),
    PRIMARY KEY (kca_call_day, entp_id)
)PARTITION BY LIST (kca_call_day);