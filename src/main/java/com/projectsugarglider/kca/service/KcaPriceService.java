package com.projectsugarglider.kca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projectsugarglider.kca.api.PriceInfo;
import com.projectsugarglider.kca.dto.KcaPriceInfoDto;
import com.projectsugarglider.kca.entity.KcaPriceInfoEntity;
import com.projectsugarglider.kca.repository.KcaPriceInfoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KcaPriceService {
    
    private final PriceInfo priceInfo;
    private final KcaPriceInfoRepository priceRepo;


    @Transactional
    public void SavePriceInfoData(){

        List<KcaPriceInfoDto> raw = priceInfo.priceEntpCall("100");
        List<KcaPriceInfoEntity> entities = getEntities(raw);
        priceRepo.saveAll(entities);
        
    }


    private List<KcaPriceInfoEntity> getEntities(
        List<KcaPriceInfoDto> raw
        ) {
            return raw.stream()
            .map(KcaPriceInfoDto::toEntity)
            .toList();
        }
}
