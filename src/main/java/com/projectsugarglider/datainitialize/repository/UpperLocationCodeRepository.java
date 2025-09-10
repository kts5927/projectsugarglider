package com.projectsugarglider.datainitialize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.datainitialize.entity.UpperLocationEntity;

@Repository
public interface UpperLocationCodeRepository extends JpaRepository<UpperLocationEntity, String> {

     /**
      * 한전 지역데이터를 업데이트 합니다. 
      * 기상청 upperCode를 Key로 가집니다.
      * 
      * @param upperCode    기상청 upperCode
      * @param kepcoCode    한전 지역코드
      */
    @Modifying
    @Query("UPDATE UpperLocationEntity u "
         + "SET u.kepcoCode = :kepcoCode "
         + "WHERE u.upperCode = :upperCode")
    int updateKepcoCodeByKey(
        @Param("upperCode") String upperCode,
        @Param("kepcoCode")  String kepcoCode
    );

    /**
     * 소비자원 데이터를 업데이트 합니다.
     * 기상청 upperCode를 Key로 가집니다.
     * 
     * @param upperCode 기상청 upperCode
     * @param kcaCode   소비자원 지역코드
     */
    @Modifying
    @Query("UPDATE UpperLocationEntity u "
         + "SET u.kcaCode = :kcaCode "
         + "WHERE u.upperCode = :upperCode")
    int updateKcaCodeByKey(
        @Param("upperCode") String upperCode,
        @Param("kcaCode")  String kcaCode
    );

    /*
     * 기상청 upper 지역코드를 List로 반환합니다.
     */
    @Query("SELECT u.weatherCode "
         + "FROM UpperLocationEntity u")
    List<String> findAllAdministrativeCodes();

}
