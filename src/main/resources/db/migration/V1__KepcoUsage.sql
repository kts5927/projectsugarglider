-- TODO : 다른테이블 초기화
CREATE TABLE IF NOT EXISTS upper_location_code (
  upper_code   VARCHAR(32) NOT NULL,
  kca_code     CHAR(4),
  kepco_code   CHAR(2),
  weather_code CHAR(2),
  PRIMARY KEY (upper_code)
);
CREATE TABLE IF NOT EXISTS lower_location_code (
  lower_code   VARCHAR(32) NOT NULL,
  upper_code   VARCHAR(32) NOT NULL,
  kca_code     CHAR(5),
  kepco_code   CHAR(2),
  weather_code CHAR(3),
  x_grid       VARCHAR(3) NOT NULL,
  y_grid       VARCHAR(3) NOT NULL,
  PRIMARY KEY (lower_code, upper_code)
);

CREATE TABLE IF NOT EXISTS kepco_usage (
  year        CHAR(4)     NOT NULL,
  month       CHAR(2)     NOT NULL,
  upper_code  VARCHAR(32) NOT NULL,
  lower_code  VARCHAR(32) NOT NULL,
  house_cnt   INT,
  power_usage INT,
  bill        INT,
  PRIMARY KEY (year, month, upper_code, lower_code),
  CONSTRAINT fk_ku_lower
    FOREIGN KEY (lower_code, upper_code)
    REFERENCES lower_location_code (lower_code, upper_code)
)
PARTITION BY RANGE (year, month);

CREATE TABLE IF NOT EXISTS weatherCodeTypeInfo (
  code  CHAR(3) NOT NULL,
  PRIMARY KEY(code)
);

-- 성능테스트 전부PK vs 지금구조
CREATE TABLE IF NOT EXISTS short_forecast_data (
  forecast_id BIGSERIAL       NOT NULL,
  year        CHAR(4)         NOT NULL,
  month       CHAR(2)         NOT NULL,
  day         CHAR(2)         NOT NULL,
  time        CHAR(4)         NOT NULL,
  code        VARCHAR(8)      NOT NULL,
  value       VARCHAR(8)      NOT NULL,
  upper_code  VARCHAR(32)     NOT NULL,
  lower_code  VARCHAR(32)     NOT NULL,

  CONSTRAINT pk_sfd
    PRIMARY KEY (year, month, day, forecast_id),

  CONSTRAINT uq_sfd
    UNIQUE (year, month, day, time, code, upper_code, lower_code),

  CONSTRAINT fk_sfd_lower
    FOREIGN KEY (lower_code, upper_code)
    REFERENCES lower_location_code (lower_code, upper_code),

  CONSTRAINT fk_sfd_code
    FOREIGN KEY (code)
    REFERENCES weatherCodeTypeInfo (code)
)
PARTITION BY RANGE (year, month, day);



