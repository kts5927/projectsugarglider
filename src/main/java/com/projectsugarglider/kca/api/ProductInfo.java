package com.projectsugarglider.kca.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kca.dto.KcaProductInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductInfo {
    
    @Value("${external.publicapi.apiKey}")
    private String apiKey;

        
    @Value("${external.kca.productinfo}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

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
