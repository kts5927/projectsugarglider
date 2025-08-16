package com.projectsugarglider.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsugarglider.weather.entity.WeatherCodeTypeEntity;

public interface WeatherCodeTypeRepository extends JpaRepository<WeatherCodeTypeEntity, String>{
    
}
