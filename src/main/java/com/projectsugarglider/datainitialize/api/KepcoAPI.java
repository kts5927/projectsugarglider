package com.projectsugarglider.datainitialize.api;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.datainitialize.dto.KepcoCommonDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KepcoAPI {

    @Value("${external.kepco.kepcokey}")
    private String apiKey;

    @Value("${external.kepco.basedataurl}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    /**
     * 한전(KEPCO) 기본 데이터를 요청합니다.
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
