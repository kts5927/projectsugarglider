package com.projectsugarglider.datainitialize.api;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.datainitialize.dto.KepcoCommonDto;

import lombok.RequiredArgsConstructor;

/**
 * KEPCO(한전) 지역 데이터 조회용 Api 어댑터
 */
@Service
@RequiredArgsConstructor
public class KepcoAPI {

    @Value("${external.kepco.kepcokey}")
    private String apiKey;

    @Value("${external.kepco.basedataurl}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    /**
     * 한전 지역 데이터를 호출합니다.
     * 
     * @return     한전 지역 데이터
     */
    public List<KepcoCommonDto> baseDataCall() {

        return apiService.getCall(
            BASE_URL,
            Map.of(
                "codeTy",     "cityCd",
                "apiKey",     apiKey,
                "returnType", "json"
            ),
            KepcoCommonDto[].class,
            false,  // JSON 응답
            "/data"
        );
    }
}
