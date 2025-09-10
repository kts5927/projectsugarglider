package com.projectsugarglider.datainitialize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.datainitialize.entity.LowerLocationEntity;
import com.projectsugarglider.datainitialize.entity.id.LowerLocationCodeId;
import com.projectsugarglider.util.dto.LocationDto;

@Repository
public interface LowerLocationCodeRepository extends JpaRepository<LowerLocationEntity, LowerLocationCodeId> {

     /**
      * lowerCode + upperCode(복합 키)로 kepcoCode를 업데이트

      * @param upperCode  기상청 upperCode
      * @param lowerCode  기상청 lowerCode  
      * @param kepcoCode  한전 지역코드
      * @return
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
    * lowerCode + upperCode(복합 키)로 kcaCode를 업데이트
    * 
    * @param upperCode  기상청 upperCode
    * @param lowerCode  기상청 lowerCode  
    * @param kcaCode    소비자원 지역코드
    * @return
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

    /**
     * 기상청 upper/lower 코드로 x/y좌표를 불러옵니다.
     * 기상청 일기예보 Param으로 사용됩니다.
     * 
     * @param upperCode 기상청 upperCode
     * @param lowerCode 기상청 lowerCode
     * @return
     */
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

}
