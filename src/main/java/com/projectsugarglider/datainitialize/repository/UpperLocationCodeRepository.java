package com.projectsugarglider.datainitialize.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;
import com.projectsugarglider.datainitialize.entity.UpperLocationEntity;

@Repository
public interface UpperLocationCodeRepository extends JpaRepository<UpperLocationEntity, String> {

    /**
     * upperCode(기본 키)로 kepcoCode만 업데이트
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
     * upperCode(기본 키)로 kcaCode만 업데이트
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
     * 법정 지역코드를 List로 반환하는 코드
     */
    @Query("SELECT u.weatherCode "
         + "FROM UpperLocationEntity u")
    List<String> findAllAdministrativeCodes();

    Optional<LowerLocationEntity> findByUpperCodeAndKcaCode(String upperCode, String kcaCode);

}
