package com.projectsugarglider.kca.service;

import org.springframework.stereotype.Service;

import com.projectsugarglider.kca.api.EntptypeDivApi;
import com.projectsugarglider.kca.api.GoodTotalDivApi;
import com.projectsugarglider.kca.api.GoodUnitDivApi;
import com.projectsugarglider.kca.dto.KcaStandardInfoDto;
import com.projectsugarglider.kca.repository.EntptypeDivRepository;
import com.projectsugarglider.kca.repository.TotalDivRepository;
import com.projectsugarglider.kca.repository.UnitDivRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KcaStandardDataSaveService {
    
    private final EntptypeDivRepository entpRepository;
    private final UnitDivRepository unitRepository;
    private final TotalDivRepository totalDivRepository;
    private final EntptypeDivApi entpType;
    private final GoodTotalDivApi totalDiv;
    private final GoodUnitDivApi unitDiv;

    @Transactional
    public void saveEntpData(){

        entpRepository.saveAll(
            entpType.entpTypeCall().stream()
                .map(KcaStandardInfoDto::toEntpEntity)
                .toList()
        );
    }

    @Transactional
    public void saveUnitData(){
        unitRepository.saveAll(
            unitDiv.unitDivCall().stream()
                .map(KcaStandardInfoDto::toUnitEntity)
                .toList()
        );

    }

    @Transactional
    public void saveTotalData(){

        totalDivRepository.saveAll(
            totalDiv.totalDivCall().stream()
                .map(KcaStandardInfoDto::toTotalEntity)
                .toList()
        );
    }




}
