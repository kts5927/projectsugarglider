package com.projectsugarglider.front.dto;

import lombok.Builder;

@Builder
public record KcaPriceResponseDto (

    String good_name,
    String good_price,
    String plusone_yn,
    String date

){}
