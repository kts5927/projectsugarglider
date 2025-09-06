CREATE TABLE weather_call_history (
    weather_call_day    CHAR(8),
    upper_code          VARCHAR(12),
    lower_code          VARCHAR(12),
    PRIMARY KEY (weather_call_day, upper_code, lower_code)
)PARTITION BY LIST (weather_call_day);
CREATE INDEX idx_weather_call_history_upper_lower
    ON weather_call_history (upper_code, lower_code);