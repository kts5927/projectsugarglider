package com.projectsugarglider.kepco.entity;

import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;
import com.projectsugarglider.kepco.entity.id.KepcoUsageEntityId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "KEPCO_usage")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@IdClass(KepcoUsageEntityId.class)
@Builder

public class KepcoUsageEntity {

    @Id
    @Column(name = "year", nullable = false)
    private String year;

    @Id
    @Column(name = "month", nullable = false)
    private String month;

    
    @Column(name = "house_Cnt")
    private Integer houseCnt;

    
    @Column(name = "power_Usage")
    private Integer powerUsage;
    
    
    @Column(name = "bill")
    private Integer bill;

    @Id
    @Column(name = "upper_code", nullable = false)
    private String upperCode;

    @Id
    @Column(name = "lower_code", nullable = false)
    private String lowerCode;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="lower_code", insertable = false, updatable = false),
        @JoinColumn(name="upper_code", insertable = false, updatable = false)
    })
    private LowerLocationEntity lowerLocationCode;

}
