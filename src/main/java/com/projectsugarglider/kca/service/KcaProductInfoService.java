package com.projectsugarglider.kca.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projectsugarglider.kca.api.ProductInfo;
import com.projectsugarglider.kca.dto.KcaProductInfoDto;
import com.projectsugarglider.kca.entity.KcaProductInfoEntity;
import com.projectsugarglider.kca.repository.ProductInfoRepository;
import com.projectsugarglider.kca.repository.TotalDivRepository;
import com.projectsugarglider.kca.repository.UnitDivRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KcaProductInfoService {
    
    private final ProductInfo productInfo;
    private final UnitDivRepository unitRepo;
    private final TotalDivRepository totalRepo;
    private final ProductInfoRepository priceRepo;

    @Transactional
    public void SaveProductInfoData(){

        List<KcaProductInfoDto> raw = productInfo.productInfoCall();
        Set<String> goodUnit = getGoodUnit();
        Set<String> totalDiv = getTotalDiv();
        List<KcaProductInfoEntity> entities = getEntities(raw, goodUnit, totalDiv);

        if (!entities.isEmpty()) {
            priceRepo.saveAll(entities);
        }
    }

    private List<KcaProductInfoEntity> getEntities(
        List<KcaProductInfoDto> raw,
        Set<String> goodUnit,
        Set<String> totalDiv
    )
    {
        List<KcaProductInfoEntity> entities = raw.stream()
        .map(dto -> {

            String test1 = dto.goodUnitDivCode();
            if (test1 != null && !goodUnit.contains(test1)){
                log.info("단위가 포함되지 않은 데이터 = {}", test1);
            }

            String test2 = dto.goodTotalDivCode();
            if (test2 != null && !goodUnit.contains(test2)){
                log.info("용량이 포함되지 않은 데이터 = {}", test2);
            }

            String test3 = dto.goodSmlclsCode();
            if (test3 != null && !totalDiv.contains(test3)){
                log.info("소분류가 포함되지 않은 데이터 = {}", test3);
            }

            return dto.toPriceEntity();
        })
        .toList();

        return entities;
    }


    private Set<String> getGoodUnit() {
        Set<String> codes = unitRepo.findAll().stream()
            .map(l -> l.getCode())
            .collect(Collectors.toSet());
        return codes;
    }

    private Set<String> getTotalDiv() {
        Set<String> codes = totalRepo.findAll().stream()
            .map(l -> l.getCode())
            .collect(Collectors.toSet());
        return codes;
    }

}
