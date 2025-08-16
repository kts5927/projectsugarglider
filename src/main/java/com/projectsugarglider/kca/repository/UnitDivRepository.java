package com.projectsugarglider.kca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsugarglider.kca.entity.KcaGoodUnitDivCodeEntity;

public interface UnitDivRepository extends JpaRepository<KcaGoodUnitDivCodeEntity, String> {
    
}
