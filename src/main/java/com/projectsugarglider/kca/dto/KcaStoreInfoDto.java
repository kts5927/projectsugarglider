package com.projectsugarglider.kca.dto;

import com.projectsugarglider.kca.entity.KcaStoreInfoEntity;


public record  KcaStoreInfoDto
(
    String entpId,
    String entpName,
    String entpTypeCode,
    String areaDetailCode,
    String entpTelno,
    String postNo,
    String plmkAddrBasic,
    String plmkAddrDetail,
    String roadAddrBasic,
    String roadAddrDetail,
    String xMapCoord,
    String yMapCoord
)
{
    public String upperKca() {
        validateAreaDetail();
        return areaDetailCode.substring(0,4);
    }
    public String lowerKca() {
        validateAreaDetail();
        return areaDetailCode.substring(4,9);
    }
    private void validateAreaDetail() {
        if (areaDetailCode == null || areaDetailCode.length() < 9) {
            throw new IllegalArgumentException("areaDetailCode는 최소 9자리여야 합니다: " + areaDetailCode);
        }
    }

    public KcaStoreInfoEntity toStoreEntity(String upperCode, String lowerCode){
        return KcaStoreInfoEntity.builder()
            .entpId(entpId)
            .entpName(entpName)
            .areaTypeCode(entpTypeCode)
            .entpTelno(entpTelno)
            .postNo(postNo)
            .plmkAddrBasic(plmkAddrBasic)
            .plmkAddrDetail(plmkAddrDetail)
            .roadAddrBasic(roadAddrBasic)
            .roadAddrDetail(roadAddrDetail)
            .xMapCoord(xMapCoord)
            .yMapCoord(yMapCoord)
            .upperCode(upperCode)
            .lowerCode(lowerCode)
            .build();
    }
}
