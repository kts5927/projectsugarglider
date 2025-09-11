package com.projectsugarglider.kca.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kca.dto.KcaProductInfoDto;

import lombok.RequiredArgsConstructor;

/**
 * KCA(소비자원) 상품 정보 조회용 API 어댑터.
 */
@Service
@RequiredArgsConstructor
public class ProductInfo {
    
    @Value("${external.publicapi.apiKey}")
    private String apiKey;

        
    @Value("${external.kca.productinfo}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    /**
     * 소비자원 상품 정보를 호출합니다. 
     * 2025년 9월 기준으로 605개의 레코드가 포함되어있습니다.
     * 
     * 자세한 정보는 다음 사이트를 확인해주시기 바랍니다.
     * https://www.price.go.kr/tprice/portal/pricenews/pricereport/priceReportList.do
     * 
     * @return 소비자원 상품 정보
     */
    public List<KcaProductInfoDto> productInfoCall(){

        return apiService.getCall(
            BASE_URL,
            Map.of(
                "serviceKey",apiKey
            ),
            KcaProductInfoDto[].class,
            true,
            "/result/item"
            );
    }

}
