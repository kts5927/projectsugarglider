package com.projectsugarglider.kca.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projectsugarglider.datainitialize.repository.LowerLocationCodeRepository;
import com.projectsugarglider.datainitialize.repository.UpperLocationCodeRepository;
import com.projectsugarglider.kca.api.StoreInfoApi;
import com.projectsugarglider.kca.dto.KcaStoreInfoDto;
import com.projectsugarglider.kca.entity.KcaStoreInfoEntity;
import com.projectsugarglider.kca.repository.StoreInfoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KcaStoreInfoService {

    private final StoreInfoApi storeInfoApi;
    private final StoreInfoRepository storeInfoRepository;
    private final UpperLocationCodeRepository upperRepository;
    private final LowerLocationCodeRepository lowerRepository;

    @Transactional
    public void saveStoreInfoData() {

        List<KcaStoreInfoDto> raw = storeInfoApi.StoreInfoCall();
        Map<String, String> upperKeyByKca = getUpperKey();
        Map<String, String> lowerCodeByPair = getLowerCode();
        List<KcaStoreInfoEntity> entities = getEntities(raw, upperKeyByKca, lowerCodeByPair);

        if (!entities.isEmpty()) {
            storeInfoRepository.saveAll(entities);
        }
    }

    private List<KcaStoreInfoEntity> getEntities(
        List<KcaStoreInfoDto> raw, 
        Map<String, String> upperKeyByKca,
        Map<String, String> lowerCodeByPair) {
        List<KcaStoreInfoEntity> entities = raw.stream()
            .filter(dto -> dto.areaDetailCode() != null && dto.areaDetailCode().length() >= 9)
            .map(dto -> {
                String upperKca = dto.upperKca();
                String lowerKca = dto.lowerKca();
                String upperKey = upperKeyByKca.get(upperKca);
                String lowerCode = lowerCodeByPair.get(upperKey + "|" + lowerKca);

                if(null == lowerCode){
                    log.info("upperKca={}, lowerKca={}, upperKey(PK)={}, lowerCode(PK)={}",
                    upperKca, lowerKca, upperKey, lowerCode);
                    }

                if (upperKey.equals("경상북도") && lowerKca.equals("15000") ){
                    return dto.toStoreEntity(upperKey, "포항시북");
                }
                else{
                    return dto.toStoreEntity(upperKey, lowerCode);
                }
                
            })
            .toList();
        return entities;
    }

    private Map<String, String> getLowerCode() {
        Map<String, String> lowerCodeByPair = lowerRepository.findAll().stream()
            .filter(l -> l.getKcaCode() != null && l.getUpperCode() != null && l.getLowerCode() != null)
            .collect(Collectors.toMap(
                l -> l.getUpperCode() + "|" + l.getKcaCode(),
                l -> l.getLowerCode(),
                (a, b) -> a
            ));
        return lowerCodeByPair;
    }

    private Map<String, String> getUpperKey() {
        Map<String, String> upperKeyByKca = upperRepository.findAll().stream()
            .filter(u -> u.getKcaCode() != null && u.getUpperCode() != null)
            .collect(Collectors.toMap(
                u -> u.getKcaCode(),
                u -> u.getUpperCode(),
                (a, b) -> a
            ));
        return upperKeyByKca;
    }
}
