-- kca_good_total_div
CREATE TABLE kca_good_total_div (
    code        CHAR(9)      NOT NULL,
    code_name   VARCHAR(255) NOT NULL,
    high_code   CHAR(9),
    CONSTRAINT pk_kca_good_total_div PRIMARY KEY (code),
    CONSTRAINT fk_kca_good_total_div_parent
        FOREIGN KEY (high_code) REFERENCES kca_good_total_div (code)
        ON DELETE SET NULL
        DEFERRABLE INITIALLY DEFERRED

);
CREATE INDEX idx_kca_good_total_div_high_code
    ON kca_good_total_div (high_code);


-- kca_good_unit_div
CREATE TABLE kca_good_unit_div (
    code        VARCHAR(255) NOT NULL,
    code_name   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_kca_good_unit_div PRIMARY KEY (code)
);


-- kca_entptype
CREATE TABLE kca_entptype (
    code        VARCHAR(255) NOT NULL,
    code_name   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_kca_entptype PRIMARY KEY (code)
);


CREATE TABLE kca_store_info (
    entp_id           VARCHAR(16)   NOT NULL,
    entp_name         VARCHAR(128) NOT NULL,
    area_type_code    VARCHAR(2),
    entp_telno        VARCHAR(32),
    post_no           VARCHAR(10),
    plmk_addr_basic   VARCHAR(255),
    plmk_addr_detail  VARCHAR(255),
    road_addr_basic   VARCHAR(255),
    road_addr_detail  VARCHAR(255),
    x_map_coord       VARCHAR(32),
    y_map_coord       VARCHAR(32),
    upper_code        VARCHAR(32)  NOT NULL,  
    lower_code        VARCHAR(32)  NOT NULL,  
    CONSTRAINT pk_kca_store_info PRIMARY KEY (entp_id),

  CONSTRAINT fk_sfd_lower
    FOREIGN KEY (lower_code, upper_code)
    REFERENCES lower_location_code (lower_code, upper_code),

    CONSTRAINT fk_kca_store__entptype
        FOREIGN KEY (area_type_code)
        REFERENCES kca_entptype (code)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE kca_product_info (
    good_id             VARCHAR(16)   NOT NULL,
    good_name           VARCHAR(128),
    good_unit_div_code  VARCHAR(255), 
    good_base_cnt       VARCHAR(8),
    good_smlcls_code    CHAR(9),      
    good_total_cnt      VARCHAR(8),
    good_total_div_code CHAR(9),     

    CONSTRAINT pk_kca_product_info PRIMARY KEY (good_id),

    CONSTRAINT fk_kpi__unit_div
        FOREIGN KEY (good_unit_div_code)
        REFERENCES kca_good_unit_div (code)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_kpi__total_div
        FOREIGN KEY (good_total_div_code)
        REFERENCES kca_good_unit_div (code)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_kpi__smlcls
        FOREIGN KEY (good_smlcls_code)
        REFERENCES kca_good_total_div (code)
        ON UPDATE CASCADE ON DELETE RESTRICT
);