package com.projectsugarglider.front.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.front.dto.KakaoPlace;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLocationFix {
    
    @Value("${external.kakao.restKey}")
    private String apiKey;

    @Value("${external.kakao.searchkeyword}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;

    public List<KakaoPlace> localDataCall(String store){
    return apiService.getCall(
        BASE_URL,
        Map.of(
            "query",store,
            "analyze_type","exact",
            "size","15",
            "page","1"
        ),
        KakaoPlace[].class,
        false,
        "/documents",
        Map.of(
            "Authorization",apiKey
        )
        );
    }
}
