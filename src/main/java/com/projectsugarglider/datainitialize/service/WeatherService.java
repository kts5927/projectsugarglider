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
import com.projectsugarglider.util.service.ApiNameFix;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Weather(기상청) 지역 데이터 저장용 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ApiNameFix nameFix;
    private final WeatherCsvReader reader;
    private final UpperLocationCodeRepository upperRepo;
    private final LowerLocationCodeRepository lowerRepo;

    /**
     * 정렬된 기상청 기준데이터를 상/하위 지역 엔티티로 만들어 일괄 저장.
     * - data는 행정코드 앞 2자리(상위코드) 기준으로 정렬되어 있음.
     */
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

            if (!upperBefore.equals(currentWeatherCode)) {
                upperBefore = currentWeatherCode;
                upperRef = row.toUpperEntity();
                uppers.add(upperRef);
            }
            lowers.add(row.toLowerEntity(upperRef,nameFix));
        }
        upperRepo.saveAll(uppers);
        lowerRepo.saveAll(lowers);
    }
}
