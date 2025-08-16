package com.projectsugarglider.datainitialize.entity;

import com.projectsugarglider.datainitialize.entity.id.LowerLocationCodeId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lower_location_code")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder
@IdClass(LowerLocationCodeId.class)
public class LowerLocationEntity {

    // 하위 지역코드
    @Id
    @Column(name = "lower_code" ,nullable = false ,length = 32)
    private String lowerCode;

    // 한전 지역코드
    @Column(name = "KEPCO_code")
    private String kepcoCode;

    // 기상청 지역코드
    @Column(name = "WEATHER_code")
    private String weatherCode;

    // 소비자원 지역코드
    @Column(name = "KCA_code")
    private String kcaCode;

    @Column(name = "x_grid")
    private String xGrid;

    @Column(name = "y_grid")
    private String yGrid;


    // 상위 지역코드
    @Id
    @Column(name = "upper_code" ,nullable = false ,length = 32)
    private String upperCode;

    @ManyToOne
    @JoinColumn(name = "upper_code")
    @MapsId("upperCode")
    private UpperLocationEntity upperLocationCode;

}
