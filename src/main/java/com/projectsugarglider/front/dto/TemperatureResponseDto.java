package com.projectsugarglider.front.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record TemperatureResponseDto (
    List<String> labels,
    List<String> data,
    String datasetLabel
){}