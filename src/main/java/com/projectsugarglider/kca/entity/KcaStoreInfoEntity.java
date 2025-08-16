package com.projectsugarglider.kca.entity;

import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kca_store_info")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class KcaStoreInfoEntity {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "entp_id", nullable = false)
    private String entpId;

    @Column(name = "entp_name", nullable = false)
    private String entpName;

    @Column(name = "area_type_code")
    private String areaTypeCode;

    @Column(name = "entp_telno")
    private String entpTelno; 

    @Column(name = "post_no")
    private String postNo;

    @Column(name = "plmk_addr_basic")
    private String plmkAddrBasic;

    @Column(name = "plmk_addr_detail")
    private String plmkAddrDetail;

    @Column(name = "road_addr_basic")
    private String roadAddrBasic;

    @Column(name = "road_addr_detail")
    private String roadAddrDetail;

    @Column(name = "x_map_coord")
    private String xMapCoord;

    @Column(name = "y_map_coord")
    private String yMapCoord;

    @Column(name = "upper_code", nullable = false)
    private String upperCode;

    @Column(name = "lower_code", nullable = false)
    private String lowerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name="lower_code", insertable = false, updatable = false),
        @JoinColumn(name="upper_code", insertable = false, updatable = false)
    })
    private LowerLocationEntity lowerLocationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_type_code", referencedColumnName = "code", insertable = false, updatable = false)
    private KcaEntpEntptypeCodeEntity EntpTypeCode;
}

