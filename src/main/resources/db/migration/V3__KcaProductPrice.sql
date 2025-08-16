CREATE TABLE kca_price_info (
    good_inspect_day    CHAR(8)         NOT NULL,   
    entp_id             varchar(16)     NOT NULL,   
    good_id             varchar(16)     NOT NULL,   
    good_price          numeric(15,0)   NOT NULL,   
    plusone_yn          char(1),  
    good_dc_yn          char(1),  
    good_dc_start_day   char(8),                        
    good_dc_end_day     char(8),                        
    input_dttm          VARCHAR(19), 

    CONSTRAINT pk_kca_price_info PRIMARY KEY (good_inspect_day, entp_id, good_id),

    CONSTRAINT fk_kprice__entp
        FOREIGN KEY (entp_id)
        REFERENCES kca_store_info (entp_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_kprice__good
        FOREIGN KEY (good_id)
        REFERENCES kca_product_info (good_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT chk_kprice__yn_plusone CHECK (plusone_yn IN ('Y','N')),
    CONSTRAINT chk_kprice__yn_dc      CHECK (good_dc_yn  IN ('Y','N')),
    CONSTRAINT chk_kprice__dc_dates   CHECK (
        (good_dc_yn='Y' AND good_dc_start_day IS NOT NULL AND good_dc_end_day IS NOT NULL)
        OR
        (good_dc_yn<>'Y' AND good_dc_start_day IS NULL AND good_dc_end_day IS NULL)
    )
)
PARTITION BY List (good_inspect_day);

CREATE INDEX idx_kpi_entp_day ON kca_price_info (entp_id, good_inspect_day);
CREATE INDEX idx_kpi_good_day ON kca_price_info (good_id, good_inspect_day);

