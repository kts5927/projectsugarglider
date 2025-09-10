package com.projectsugarglider.datainitialize.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BaseDataService{
    //TODO: 서버 개발중에는 Controller를통해서 하지만 개발이 끝나면 main실행에 포함시키기
    private final WeatherService weatherLocationSaveService;
    private final KcaService kcaLocationSaveService;
    private final KepcoService kepcoLocationSaveService;

    /**
     * 지역데이터를 통합저장하는 코드
     * 기상청, 소비자원, 한전 데이터를 한번에 업데이트합니다.
     */
    public void saveAllLocations()  {
        weatherLocationSaveService.updateBaseWeatherData();
        kcaLocationSaveService.updateBaseKcaData();
        kepcoLocationSaveService.updateBaseKepcoData();
    }


}
