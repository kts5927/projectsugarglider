package com.projectsugarglider.weather.dto;

public record ShortForecastRequest(
    String upperCode,
    String lowerCode
) {}