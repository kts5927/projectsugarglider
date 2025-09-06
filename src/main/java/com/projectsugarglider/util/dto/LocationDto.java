package com.projectsugarglider.util.dto;

import lombok.Builder;

@Builder
public record LocationDto(
    String lowerCode,
    String upperCode,
    String nx,
    String ny
){}
