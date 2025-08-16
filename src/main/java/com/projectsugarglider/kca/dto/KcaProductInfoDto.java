package com.projectsugarglider.kca.dto;

import com.projectsugarglider.kca.entity.KcaProductInfoEntity;


public record KcaProductInfoDto(

String goodId,
String goodName,
String goodUnitDivCode,
String goodBaseCnt,
String goodSmlclsCode,
String goodTotalCnt,
String goodTotalDivCode
){

    public KcaProductInfoEntity toPriceEntity(){
        return KcaProductInfoEntity.builder()
        .goodId(goodId)
        .goodName(goodName)
        .goodUnitDivCode(goodUnitDivCode)
        .goodBaseCnt(goodBaseCnt)
        .goodSmlclsCode(goodSmlclsCode)
        .goodTotalCnt(goodTotalCnt)
        .goodTotalDivCode(goodTotalDivCode)
        .build();
    }
}