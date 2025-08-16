package com.projectsugarglider.kca.dto;

import java.time.LocalDateTime;

import com.projectsugarglider.kca.entity.KcaPriceInfoEntity;

public record KcaPriceInfoDto(
    String goodInspectDay, 
    String entpId,
    String goodId,
    Long   goodPrice,
    String plusoneYn,      
    String goodDcYn,       
    String goodDcStartDay, 
    String goodDcEndDay,   
    String inputDttm       
) {

    public KcaPriceInfoEntity toEntity() {
        return KcaPriceInfoEntity.builder()
            .goodInspectDay(goodInspectDay)
            .entpId(entpId)
            .goodId(goodId)
            .goodPrice(goodPrice)
            .plusoneYn(plusoneYn)
            .goodDcYn(goodDcYn)
            .goodDcStartDay(goodDcStartDay)
            .goodDcEndDay(goodDcEndDay)
            .inputDttm(inputDttm)
            .build();
    }

    public static KcaPriceInfoDto fromEntity(KcaPriceInfoEntity e) {
        return new KcaPriceInfoDto(
            e.getGoodInspectDay(),
            e.getEntpId(),
            e.getGoodId(),
            e.getGoodPrice(),
            e.getPlusoneYn(),
            e.getGoodDcYn(),
            e.getGoodDcStartDay(),
            e.getGoodDcEndDay(),
            e.getInputDttm()
        );
    }
}
