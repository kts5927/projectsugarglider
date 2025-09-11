package com.projectsugarglider.kca.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kca.dto.KcaStoreInfoDto;

import lombok.RequiredArgsConstructor;

/**
 * KCA(소비자원) 업체 정보 조회용 API 어댑터.
 */
@Service
@RequiredArgsConstructor
public class StoreInfoApi {
    
    @Value("${external.publicapi.apiKey}")
    private String apiKey;

        
    @Value("${external.kca.storeinfo}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    /**
     * 소비자원 업체 정보를 호출합니다.
     * 
     * 주의
     * 
     * - 정보의 많은 부분이 빠져있습니다. 
     * - x/y좌표도 바뀌어있는경우가 상당수입니다.
     * - 만약 사용한다면 추가적인 보정작업이 필요합니다.
     * 
     * 본 프로젝트에서는 카카오 API를 통해 부족한 데이터를 보충하고, 
     * 로직을 활용해 x/y좌표를 보정하였습니다.
     * 
     * @return 소비자원 업체정보
     */
    public List<KcaStoreInfoDto> StoreInfoCall(){

        return apiService.getCall(
            BASE_URL,
            Map.of(
                "serviceKey",apiKey
            ),
            KcaStoreInfoDto[].class,
            true,
            "/result/iros.openapi.service.vo.entpInfoVO"
            );
    }

}
