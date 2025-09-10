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

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ApiNameFix nameFix;
    private final WeatherCsvReader reader;
    private final UpperLocationCodeRepository upperRepo;
    private final LowerLocationCodeRepository lowerRepo;

    /**
     * 기상청 데이터를 업데이트 합니다.
     * 기상청 지역데이터는 csv데이터 기반이라 정렬되어 있습니다.
     * 데이터는 행정코드/상위지역명/하위지역명/x좌표/y좌표가 묶여져 있습니다.
     * 따라서 하나의 상위지역 데이터가 모두 나왔을때 다음 데이터가 나옵니다.
     * (예시/ 서울특별시 데이터... 대전광역시 데이터...)
     * 
     * 중복탐색을 방지하기 위해 upperBefore String값을 사용하여
     * 상위지역코드가 다 끝났을때(upperBefore != currentWeatherCode)
     * 상위코드를 새로운 지역코드로 갱신하게 됩니다.
     * 
     * 또한 데이터의 Batch처리를 위해
     * ArrayList에 데이터를 저장한 후, 
     * 로직이 끝났을때 한꺼번에 DB로 저장합니다.
     */
    @Transactional
    public void updateBaseWeatherData() {
        List<WeatherCommonDto> data = reader.baseDataCall();
        List<UpperLocationEntity> uppers = new ArrayList<>();
        List<LowerLocationEntity> lowers = new ArrayList<>();

        String upperBefore = "00";
        UpperLocationEntity upperRef = null;

        //불러온 데이터
        for (WeatherCommonDto row : data) {
            String 행정코드 = row.district();
            String currentWeatherCode = 행정코드.substring(0, 2);

            // 상위지역이 다르다면(새로운 상위지역이 나왔다면) 저장할 상위 지역에 추가
            if (!upperBefore.equals(currentWeatherCode)) {
                upperBefore = currentWeatherCode;
                upperRef = row.toUpperEntity();
                uppers.add(upperRef);
            }
            //저장할 하위 지역에 추가
            lowers.add(row.toLowerEntity(upperRef,nameFix));
        }
        upperRepo.saveAll(uppers);
        lowerRepo.saveAll(lowers);
    }
}
