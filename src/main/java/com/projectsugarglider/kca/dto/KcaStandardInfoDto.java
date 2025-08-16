package com.projectsugarglider.kca.dto;

import com.projectsugarglider.kca.entity.KcaEntpEntptypeCodeEntity;
import com.projectsugarglider.kca.entity.KcaGoodTotalDivCodeEntity;
import com.projectsugarglider.kca.entity.KcaGoodUnitDivCodeEntity;

public record KcaStandardInfoDto 
(
    String code,
    String codeName,
    String highCode

)
{

    public KcaEntpEntptypeCodeEntity toEntpEntity() {
        return KcaEntpEntptypeCodeEntity.builder()
            .code(this.code)
            .codeName(this.codeName)
            .build();
    }

    public KcaGoodUnitDivCodeEntity toUnitEntity() {
        return KcaGoodUnitDivCodeEntity.builder()
            .code(this.code)
            .codeName(this.codeName)
            .build();
    }

    public KcaGoodTotalDivCodeEntity toTotalEntity(){
        return KcaGoodTotalDivCodeEntity.builder()
            .code(this.code)
            .codeName(this.codeName)
            .highCode(this.highCode)
            .build();
    }
}
