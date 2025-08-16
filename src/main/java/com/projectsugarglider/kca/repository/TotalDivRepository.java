package com.projectsugarglider.kca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projectsugarglider.kca.entity.KcaGoodTotalDivCodeEntity;


@Repository
public interface TotalDivRepository extends JpaRepository<KcaGoodTotalDivCodeEntity, String> {
    
}
