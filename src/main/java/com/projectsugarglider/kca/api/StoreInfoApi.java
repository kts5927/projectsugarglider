package com.projectsugarglider.kca.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kca.dto.KcaStoreInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreInfoApi {
    
    @Value("${external.publicapi.apiKey}")
    private String apiKey;

        
    @Value("${external.kca.storeinfo}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

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
