package com.projectsugarglider.weather.dto;

import com.projectsugarglider.util.dto.LocationDto;
import com.projectsugarglider.weather.entity.ShortTimeForecastEntity;

public record ShortTimeForecastDto(
    String baseDate,
    String baseTime,
    String category,
    String fcstDate,
    String fcstTime,
    String fcstValue,
    String nx,
    String ny
) {
    /** DTO → ShortTimeForecastEntity */
    public ShortTimeForecastEntity toEntity(ShortTimeForecastDto dto, LocationDto loc) {
        return ShortTimeForecastEntity.builder()
            .year (dto.fcstDate().substring(0,4))
            .month(dto.fcstDate().substring(4,6))
            .day  (dto.fcstDate().substring(6,8))
            .time (dto.fcstTime())
            .code (dto.category())
            .value(dto.fcstValue())
            .upperCode(loc.upperCode())
            .lowerCode(loc.lowerCode())
            .build();
    }

}