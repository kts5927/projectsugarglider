package com.projectsugarglider.front.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.front.entity.WeatherCallHistory;
import com.projectsugarglider.front.entity.id.WeatherCallHistoryId;

@Repository
public interface  WeatherCallHistoryRepository extends JpaRepository<WeatherCallHistory, WeatherCallHistoryId>{
    
    boolean existsByWeatherCallDayAndUpperCodeAndLowerCode(
        String weatherCallDay, String upperCode, String lowerCode
    );

}
