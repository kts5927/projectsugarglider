package com.projectsugarglider.front.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectsugarglider.front.entity.KcaCallHistory;

public interface  KcaCallHistoryRepository extends JpaRepository<KcaCallHistory, String>{
    
    boolean existsByKcaCallDayAndEntpId(
        String kcaCallDay, String entpId
    );

}
