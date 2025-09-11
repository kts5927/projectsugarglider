package com.projectsugarglider.kca.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kca.dto.KcaStandardInfoDto;

import lombok.RequiredArgsConstructor;

/**
 * KCA(소비자원) 상품 단위정보 조회용 API 어댑터.
 */
@Service
@RequiredArgsConstructor
public class GoodUnitDivApi {
    
    @Value("${external.publicapi.apiKey}")
    private String apiKey;

        
    @Value("${external.kca.totaldiv}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    /**
     * 소비자원 상품 단위구분 데이터를 호출합니다.
     * 
     * @return 소비자원 상품 단위구분 데이터
     */
    public List<KcaStandardInfoDto> unitDivCall(){

        return apiService.getCall(
            BASE_URL,
            Map.of(
                "classCode","UT",
                "serviceKey",apiKey
            ),
            KcaStandardInfoDto[].class,
            true,
            "/result/iros.openapi.service.vo.stdInfoVO"
            );
    }

}
