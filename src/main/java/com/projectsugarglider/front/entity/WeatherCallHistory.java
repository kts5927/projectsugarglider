package com.projectsugarglider.front.entity;

import com.projectsugarglider.front.entity.id.WeatherCallHistoryId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "weather_call_history")
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@IdClass(WeatherCallHistoryId.class)
@Builder
public class WeatherCallHistory {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "weather_call_day", length = 8)
    private String weatherCallDay;

    @Id
    @Column(name = "upper_code")
    private String upperCode;

    @Id
    @Column(name = "lower_code")
    private String lowerCode;

}
