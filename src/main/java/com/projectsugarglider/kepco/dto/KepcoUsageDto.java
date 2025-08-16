package com.projectsugarglider.kepco.dto;

import java.io.Serializable;

import com.projectsugarglider.kepco.entity.KepcoUsageEntity;
import com.projectsugarglider.util.service.ApiNameFix;



public record KepcoUsageDto(
    String metro,
    String city,
    Integer houseCnt,
    Integer powerUsage, 
    Integer bill
) implements Serializable {

    public static KepcoUsageDto from(KepcoUsageEntity e) {
        return new KepcoUsageDto(
            e.getUpperCode(),
            e.getLowerCode(),
            e.getHouseCnt(),
            e.getPowerUsage(),
            e.getBill()
        );
    }

    public KepcoUsageEntity toUsageEntity(
        String year,
        String month,
        ApiNameFix nameFix
    ){
        String fixedMetro = nameFix.fixUpper(this.metro);
        String fixedCity = ApiNameFix.fixLower(this.city);
        return KepcoUsageEntity.builder()
            .year(year)
            .month(month)
            .houseCnt(this.houseCnt)
            .powerUsage(this.powerUsage)
            .bill(this.bill)
            .upperCode(fixedMetro)
            .lowerCode(fixedCity)
            .build();

    }
}
