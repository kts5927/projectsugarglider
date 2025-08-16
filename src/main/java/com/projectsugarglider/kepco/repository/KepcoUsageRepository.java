package com.projectsugarglider.kepco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectsugarglider.kepco.entity.KepcoUsageEntity;
import com.projectsugarglider.kepco.entity.id.KepcoUsageEntityId;

@Repository
public interface KepcoUsageRepository
    extends JpaRepository<KepcoUsageEntity, KepcoUsageEntityId> {

            /**
     * year, month 로 조회
     * @param year  "2024"
     * @param month "07"
     */
    List<KepcoUsageEntity> findByYearAndMonth(String year, String month);

}