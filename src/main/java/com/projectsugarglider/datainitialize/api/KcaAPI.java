package com.projectsugarglider.datainitialize.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.datainitialize.dto.KcaCommonDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KcaAPI {

    @Value("${external.publicapi.apiKey}")
    private String apiKey;

    @Value("${external.kca.basedataurl}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    /**
     * 소비자원 지역 데이터를 호출합니다.
     * DataInitialize에서는 지역데이터를, 
     * kca.api에서는 상품종류, 판매처 등의 데이터를 호출합니다.
     * 
     * 호출파라미터
     * classCode : AR
     * 
     * @return     소비자원 지역 데이터
     */
    public List<KcaCommonDto> baseDataCall() {
        return apiService.getCall(
            BASE_URL,
            Map.of(
                "classCode",  "AR",
                "ServiceKey", apiKey
            ),
            KcaCommonDto[].class,
            true,   // XML 응답
            "/result/iros.openapi.service.vo.stdInfoVO"
        );
    }
}
