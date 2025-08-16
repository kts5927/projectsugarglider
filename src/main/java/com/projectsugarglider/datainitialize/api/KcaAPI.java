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
     * 소비자원(KCA) 기본 데이터를 요청합니다.
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
