package com.projectsugarglider.front.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projectsugarglider.front.dto.KcaPriceResponseDto;
import com.projectsugarglider.front.entity.KcaCallHistory;
import com.projectsugarglider.front.repository.KcaCallHistoryRepository;
import com.projectsugarglider.kca.service.KcaPriceService;
import com.projectsugarglider.util.service.DateTime;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KcaCallHistoryCheck {
    
    private final KcaCallHistoryRepository repo;
    private final DateTime time;
    private final KcaPriceService price;
    private final kcaPriceResponseService response;

    @Transactional
    public List<KcaPriceResponseDto> service(String entpId){
        String now = time.kstNowYYYYMMDD();

        if(!repo.existsByKcaCallDayAndEntpId(entpId, entpId)){
            KcaCallHistory DTO = KcaCallHistory.builder()
            .kcaCallDay(now)
            .entpId(entpId)
            .build();

            repo.save(DTO);
            price.SavePriceInfoData(entpId);

        }
        return response.listByEntpId(entpId);


    }
}
