package com.projectsugarglider.datainitialize.dto;


public record KepcoCommonDto (
    String uppoCd,    // 상위코드
    String uppoCdNm,  // 상위코드명
    String codeTy,    // 코드유형
    String code,      // 코드값
    String codeNm    // 코드명
){}