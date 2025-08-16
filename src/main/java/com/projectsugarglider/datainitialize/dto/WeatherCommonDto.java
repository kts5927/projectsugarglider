package com.projectsugarglider.datainitialize.dto;

import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;
import com.projectsugarglider.datainitialize.entity.UpperLocationEntity;
import com.projectsugarglider.util.service.ApiNameFix;

public record WeatherCommonDto (
    String district,
    String upperCode,
    String lowerCode,
    String xGrid,
    String yGrid
 ) {

        /** 상위 코드용 엔티티 변환 */
    public UpperLocationEntity toUpperEntity() {
        String upperWeatherCode = district().substring(0, 2);
        return UpperLocationEntity.builder()
                .upperCode(upperCode())
                .weatherCode(upperWeatherCode)
                .build();
    }

    /** 하위 코드용 엔티티 변환 */
    public LowerLocationEntity toLowerEntity(UpperLocationEntity upperRef) {
        String lowerWeatherCode = district().substring(2, 5);
        String fixedLowerCode = ApiNameFix.fixLower(this.lowerCode());
        return LowerLocationEntity.builder()
                .lowerCode(fixedLowerCode)
                .weatherCode(lowerWeatherCode)
                .upperCode(upperCode())
                .upperLocationCode(upperRef)
                .xGrid(xGrid())
                .yGrid(yGrid())
                .build();
    }
 }
