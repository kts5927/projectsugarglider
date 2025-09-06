package com.projectsugarglider.front.dto;

public record KakaoPlace(
    String address_name,
    String category_group_code,
    String category_group_name,
    String category_name,
    String id,
    String phone,
    String place_name,
    String place_url,
    String road_address_name,
    String x,
    String y
) {}