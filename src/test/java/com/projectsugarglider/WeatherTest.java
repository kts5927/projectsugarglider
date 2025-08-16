package com.projectsugarglider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.projectsugarglider.datainitialize.repository.LowerLocationCodeRepository;
import com.projectsugarglider.util.dto.LocationDto;
import com.projectsugarglider.weather.service.ShortTimeForecastService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WeatherTest {
    
    // @Autowired
    // private ShortTimeForecastApi forecast;

    @Autowired
    private ShortTimeForecastService service;

    @Autowired
    private LowerLocationCodeRepository lower;
    


    @Test
    void locationCheck(){
        log.info("return : {}",lower.findAllLocationWithWeather());
    }

    @Test
    void saveTest(){
        LocationDto mok = new LocationDto(
            "관악구", 
            "서울특별시", 
            "59", 
            "125");
        service.saveAllShortTimeForecast(mok);
        log.info("작업완료");
    }


}
