package com.projectsugarglider.kca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "kca_product_info")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder
public class KcaProductInfoEntity {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "good_id", nullable = false, length = 16)
    private String goodId;

    @Column(name = "good_name", length = 128)
    private String goodName;

    @Column(name = "good_unit_div_code")
    private String goodUnitDivCode;

    @Column(name = "good_base_cnt", length = 8)
    private String goodBaseCnt;

    @Column(name = "good_smlcls_code", length = 9)
    private String goodSmlclsCode;

    @Column(name = "good_total_cnt", length = 8)
    private String goodTotalCnt;

    @Column(name = "good_total_div_code", length = 9)
    private String goodTotalDivCode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_unit_div_code", referencedColumnName = "code",
                insertable = false, updatable = false)
    private KcaGoodUnitDivCodeEntity goodUnitDiv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_total_div_code", referencedColumnName = "code",
                insertable = false, updatable = false)
    private KcaGoodUnitDivCodeEntity goodTotalDiv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_smlcls_code", referencedColumnName = "code",
                insertable = false, updatable = false)
    private KcaGoodTotalDivCodeEntity goodSmlcls;

}
