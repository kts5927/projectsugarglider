package com.projectsugarglider.kca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsugarglider.kca.entity.KcaStoreInfoEntity;

public interface  StoreInfoRepository extends JpaRepository<KcaStoreInfoEntity, String> {
    
}
