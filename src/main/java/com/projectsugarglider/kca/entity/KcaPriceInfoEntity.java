package com.projectsugarglider.kca.entity;

import java.time.LocalDateTime;

import com.projectsugarglider.kca.entity.id.KcaPriceInfoId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kca_price_info")
@IdClass(KcaPriceInfoId.class)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class KcaPriceInfoEntity {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "good_inspect_day", nullable = false, length = 8)
    private String goodInspectDay;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "entp_id", nullable = false, length = 16)
    private String entpId;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "good_id", nullable = false, length = 16)
    private String goodId;

    @Column(name = "good_price", nullable = false)
    private Long goodPrice;

    @Column(name = "plusone_yn", length = 1)
    private String plusoneYn; 

    @Column(name = "good_dc_yn", length = 1)
    private String goodDcYn;  

    @Column(name = "good_dc_start_day", length = 8)
    private String goodDcStartDay;

    @Column(name = "good_dc_end_day", length = 8)
    private String goodDcEndDay;

    @Column(name = "input_dttm", length = 32)
    private String inputDttm;

    // ---------- FK (읽기 전용) ----------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entp_id", referencedColumnName = "entp_id", insertable = false, updatable = false)
    private KcaStoreInfoEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id", referencedColumnName = "good_id", insertable = false, updatable = false)
    private KcaProductInfoEntity product;
}
