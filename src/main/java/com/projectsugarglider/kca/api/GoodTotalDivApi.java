package com.projectsugarglider.kca.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kca.dto.KcaStandardInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoodTotalDivApi {
    
    @Value("${external.publicapi.apiKey}")
    private String apiKey;

        
    @Value("${external.kca.totaldiv}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    public List<KcaStandardInfoDto> totalDivCall(){

        return apiService.getCall(
            BASE_URL,
            Map.of(
                "classCode","AL",
                "serviceKey",apiKey
            ),
            KcaStandardInfoDto[].class,
            true,
            "/result/iros.openapi.service.vo.stdInfoVO"
            );
    }

}
