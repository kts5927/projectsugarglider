package com.projectsugarglider.weather.entity;

import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(
    name = "shortForecastData",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_sfd",
        columnNames = {"year","month","day","time","code","upper_code","lower_code"}
    )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = "forecastId")
public class ShortTimeForecastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forecast_id", nullable = false)
    private Long forecastId;

    @Column(name = "year",   length = 4,  nullable = false)
    private String year;

    @Column(name = "month",  length = 2,  nullable = false)
    private String month;

    @Column(name = "day",    length = 2,  nullable = false)
    private String day;

    @Column(name = "time",   length = 4,  nullable = false)
    private String time;

    @Column(name = "code",   length = 8,  nullable = false)
    private String code;

    @Column(name = "value",  length = 8,  nullable = false)
    private String value;

    @Column(name = "upper_code", length = 32, nullable = false)
    private String upperCode;

    @Column(name = "lower_code", length = 32, nullable = false)
    private String lowerCode;

    // lower_location_code 테이블과 다대일 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "lower_code", referencedColumnName = "lower_code", insertable = false, updatable = false),
        @JoinColumn(name = "upper_code", referencedColumnName = "upper_code",          insertable = false, updatable = false)
    })
    private LowerLocationEntity lowerLocation;

    // weatherCodeTypeInfo 테이블과 다대일 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code", referencedColumnName = "code", insertable = false, updatable = false)
    private WeatherCodeTypeEntity weatherCodeTypeInfo;


    
}