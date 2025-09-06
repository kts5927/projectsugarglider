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
    public void SavePriceInfoData(String entp){

        List<KcaPriceInfoDto> raw = priceInfo.priceEntpCall(entp);
        List<KcaPriceInfoEntity> entities = getEntities(raw).stream()
            .map(e -> !"Y".equalsIgnoreCase(e.getGoodDcYn())
                ? KcaPriceInfoEntity.builder()
                    .goodInspectDay(e.getGoodInspectDay())
                    .entpId(e.getEntpId())
                    .goodId(e.getGoodId())
                    .goodPrice(e.getGoodPrice())
                    .plusoneYn(e.getPlusoneYn())
                    .goodDcYn(e.getGoodDcYn())
                    .goodDcStartDay(null)
                    .goodDcEndDay(null)
                    .inputDttm(e.getInputDttm())
                    .build()
                : e)
            .toList();
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
