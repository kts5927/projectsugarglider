package com.projectsugarglider.datainitialize.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projectsugarglider.datainitialize.api.WeatherCsvReader;
import com.projectsugarglider.datainitialize.dto.WeatherCommonDto;
import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;
import com.projectsugarglider.datainitialize.entity.UpperLocationEntity;
import com.projectsugarglider.datainitialize.repository.LowerLocationCodeRepository;
import com.projectsugarglider.datainitialize.repository.UpperLocationCodeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherCsvReader reader;
    private final UpperLocationCodeRepository upperRepo;
    private final LowerLocationCodeRepository lowerRepo;

    @Transactional
    public void updateBaseWeatherData() {
        List<WeatherCommonDto> data = reader.baseDataCall();
        List<UpperLocationEntity> uppers = new ArrayList<>();
        List<LowerLocationEntity> lowers = new ArrayList<>();

        String upperBefore = "00";
        UpperLocationEntity upperRef = null;

        for (WeatherCommonDto row : data) {
            String 행정코드 = row.district();
            String currentWeatherCode = 행정코드.substring(0, 2);

            // 상위 지역 중복 확인
            if (!upperBefore.equals(currentWeatherCode)) {
                upperBefore = currentWeatherCode;
                upperRef = row.toUpperEntity();
                uppers.add(upperRef);
            }
            lowers.add(row.toLowerEntity(upperRef));
        }
        upperRepo.saveAll(uppers);
        lowerRepo.saveAll(lowers);
    }
}
