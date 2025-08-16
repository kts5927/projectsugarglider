package com.projectsugarglider.datainitialize.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;
import com.projectsugarglider.datainitialize.entity.id.LowerLocationCodeId;
import com.projectsugarglider.util.dto.LocationDto;
import com.projectsugarglider.weather.dto.LowerUpperWeatherDto;

@Repository
public interface LowerLocationCodeRepository extends JpaRepository<LowerLocationEntity, LowerLocationCodeId> {

    /**
     * lowerLocationCode + upperCode(복합 키)로 kepcoCode만 업데이트
     */
    @Modifying
    @Query("UPDATE LowerLocationEntity l "
         + "SET l.kepcoCode = :kepcoCode "
         + "WHERE l.lowerCode = :lowerCode "
         + "  AND l.upperCode = :upperCode")
    int updateKepcoCodeByKey(
        @Param("upperCode") String upperCode,
        @Param("lowerCode") String lowerCode,
        @Param("kepcoCode") String kepcoCode
    );

        /**
     * lowerLocationCode + upperCode(복합 키)로 kcaCode만 업데이트
     */
    @Modifying
    @Query("UPDATE LowerLocationEntity l "
         + "SET l.kcaCode = :kcaCode "
         + "WHERE l.lowerCode = :lowerCode "
         + "  AND l.upperCode = :upperCode")
    int updateKcaCodeByKey(
        @Param("upperCode") String upperCode,
        @Param("lowerCode") String lowerCode,
        @Param("kcaCode") String kcaCode
    );

    @Query("""
        select new com.projectsugarglider.util.dto.LocationDto(
            l.lowerCode,     
            l.upperCode,    
            l.xGrid,           
            l.yGrid         
        )
        from LowerLocationEntity l
        where l.upperCode = :upperCode
          and l.lowerCode = :lowerCode
    """)
    LocationDto findLocationWithCode(
        @Param("upperCode") String upperCode,
        @Param("lowerCode") String lowerCode
    );

    @Query("""
      SELECT new com.projectsugarglider.weather.dto.LowerUpperWeatherDto(
        l.weatherCode,
        u.weatherCode
      )
      FROM LowerLocationEntity l
      JOIN l.upperLocationCode u
    """)
    List<LowerUpperWeatherDto> findAllLowerUpperWithWeather();

    @Query("""
      SELECT new com.projectsugarglider.util.dto.LocationDto(
        l.lowerCode,
        l.upperCode,
        l.xGrid,
        l.yGrid
      )
      FROM LowerLocationEntity l
    """)
    List<LocationDto> findAllLocationWithWeather();


    Optional<LowerLocationEntity> findByUpperCodeAndKcaCode(String upperCode, String kcaCode);


}
