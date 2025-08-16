package com.projectsugarglider.kca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsugarglider.kca.entity.KcaProductInfoEntity;

public interface ProductInfoRepository extends JpaRepository<KcaProductInfoEntity, String>{
    
}
