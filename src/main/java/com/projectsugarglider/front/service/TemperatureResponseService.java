package com.projectsugarglider.front.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projectsugarglider.front.dto.TemperatureResponseDto;
import com.projectsugarglider.util.service.DateTime;
import com.projectsugarglider.weather.entity.ShortTimeForecastEntity;
import com.projectsugarglider.weather.repository.ShortTimeForecastRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemperatureResponseService {
    final String TEMP_CODE = "TMP";

    private final ShortTimeForecastRepository repo;
    private final DateTime time;

    public TemperatureResponseDto service(String upper, String lower){

        String[] tmdt = time.toYYYYMMDDHHMM(time.kstNow());
        String y = tmdt[0];
        String m = tmdt[1];
        String d = tmdt[2];

        List<ShortTimeForecastEntity> rows = getRows(upper, lower, y, m, d);
        List<String> labels = getLabels(rows);
        List<String> data = getData(rows);
        String datasetLabel = "기온(℃) - " + lower; 
        return extracted(labels, data, datasetLabel);

    }


    private TemperatureResponseDto extracted(List<String> labels, List<String> data, String datasetLabel) {
        return TemperatureResponseDto.builder()
                .labels(labels)
                .data(data)
                .datasetLabel(datasetLabel)
                .build();
    }

    private List<String> getData(List<ShortTimeForecastEntity> rows) {
        List<String> data = rows.stream()
                .map(e -> e.getValue())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return data;
    }

    private List<String> getLabels(List<ShortTimeForecastEntity> rows) {
        List<String> labels = rows.stream()
                .map(e -> formatLabel(e.getTime()))
                .collect(Collectors.toList());
        return labels;
    }

    private List<ShortTimeForecastEntity> getRows(String upper, String lower, String y, String m, String d) {
        List<ShortTimeForecastEntity> rows =
                repo.findByUpperCodeAndLowerCodeAndCodeAndYearAndMonthAndDayOrderByTimeAsc(
                        upper, lower, TEMP_CODE, y, m, d
                );
        return rows;
    }

    private static String formatLabel(String hhmm) {
        return hhmm.substring(0, 2) + ":" + hhmm.substring(2);
        
    }
}