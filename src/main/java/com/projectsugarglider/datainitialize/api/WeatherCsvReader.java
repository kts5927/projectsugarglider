package com.projectsugarglider.datainitialize.api;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReaderHeaderAware;
import com.projectsugarglider.datainitialize.dto.WeatherCommonDto;

@Service
public class WeatherCsvReader {

    public List<WeatherCommonDto> baseDataCall() {
        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(
                new InputStreamReader(new ClassPathResource("기상청데이터.csv").getInputStream(), "UTF-8")
        )) {
                List<WeatherCommonDto> result = new ArrayList<>();
                Map<String, String> line;
                while ((line = reader.readMap()) != null) {
                    WeatherCommonDto dto = new WeatherCommonDto(
                        line.get("행정구역코드"),
                        line.get("1단계"),
                        line.get("2단계"),
                        line.get("격자 X"),
                        line.get("격자 Y")
                    );
                    result.add(dto);
            }
            return result;

        } catch (Exception e) {
            return List.of();
        }
    }
}
