package com.projectsugarglider.kca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.kca.entity.KcaEntpEntptypeCodeEntity;

@Repository
public interface EntptypeDivRepository extends JpaRepository<KcaEntpEntptypeCodeEntity,String>{
    
}
