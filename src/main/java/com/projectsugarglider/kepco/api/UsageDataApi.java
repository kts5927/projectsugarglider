package com.projectsugarglider.kepco.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kepco.dto.KepcoUsageDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsageDataApi {
    
    @Value("${external.kepco.kepcokey}")
    private String apiKey;

    @Value("${external.kepco.usagedataurl}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    public List<KepcoUsageDto> usageDataCall(
        String year,
        String month,
        String metroCd
        ){

            return apiService.getCall(
                BASE_URL, 
                Map.of(
                    "year",year,
                    "month",month,
                    "metroCd",metroCd,
                    "apiKey",apiKey
                ), 
                KepcoUsageDto[].class, 
                false, 
                "/data"
            );
    }
}
