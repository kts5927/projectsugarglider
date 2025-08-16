package com.projectsugarglider.weather.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.weather.dto.ShortTimeForecastDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShortTimeForecastApi{

   @Value("${external.publicapi.apiKey}")
    private String apiKey;

    @Value("${external.weather.shorttime}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;
 
    // TODO: base_date/base_time 자료형 확인
    // 날짜 자동설정 기능   
    // DB에서 nx/ny 불러오기
    // 11/305는 서울특별시 강북구
    public List<ShortTimeForecastDto> forecastCall(
        String nx,
        String ny,
        String now
    ){
        return apiService.getCall(
            BASE_URL, 
            Map.<String,String>of(
            "serviceKey",apiKey,
            "numOfRows", "1000",
            "pageNo", "1",
            "dataType","JSON",
            "base_date",now,
            "base_time","0500",
            "nx",nx,
            "ny",ny
            ),
            ShortTimeForecastDto[].class,
            false,
             "/response/body/items/item"
             );
    }
}
