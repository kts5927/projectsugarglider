package com.projectsugarglider.datainitialize.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "upper_location_code")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Builder
public class UpperLocationEntity {

    // 상위 지역코드
    @Id
    @Column(name = "upper_code" ,nullable = false ,length = 32)
    private String upperCode;

    // 한전 지역코드
    @Column(name = "KEPCO_code")
    private String kepcoCode;

    // 기상청 지역코드
    @Column(name = "WEATHER_code")
    private String weatherCode;

    // 소비자원 지역코드
    @Column(name = "KCA_code")
    private String kcaCode;

}