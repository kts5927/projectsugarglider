package com.projectsugarglider.kca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.kca.entity.KcaPriceInfoEntity;

@Repository
public interface KcaPriceInfoRepository extends JpaRepository<KcaPriceInfoEntity, String>{

    List<KcaPriceInfoEntity> findByEntpId(String entpId);
    
}