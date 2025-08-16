package com.projectsugarglider.datainitialize.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projectsugarglider.datainitialize.api.KepcoAPI;
import com.projectsugarglider.datainitialize.dto.KepcoCommonDto;
import com.projectsugarglider.datainitialize.repository.LowerLocationCodeRepository;
import com.projectsugarglider.datainitialize.repository.UpperLocationCodeRepository;
import com.projectsugarglider.util.service.ApiNameFix;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KepcoService {

    private final KepcoAPI kepcoAPI;
    private final UpperLocationCodeRepository upperRepo;
    private final LowerLocationCodeRepository lowerRepo;
    private final ApiNameFix nameFix;

    @Transactional
    public void updateBaseKepcoData() {
        // 1) cityCd API 호출
        List<KepcoCommonDto> list = kepcoAPI.baseDataCall();

        // 2) 각 DTO에 담긴 key값으로 업데이트
        for (KepcoCommonDto dto : list) {
            String upperKey  = nameFix.fixUpper(dto.uppoCdNm()); 
            String lowerKey  = nameFix.fixLower(dto.codeNm());   
            String uppocode  = dto.uppoCd();
            String lowercode = dto.code();  

            // 상위 테이블(upper_code 기준)
            upperRepo.updateKepcoCodeByKey(upperKey, uppocode);

            // 하위 테이블(lower_location_code + upper_code 기준)
            lowerRepo.updateKepcoCodeByKey(upperKey, lowerKey, lowercode);
        }
    }
}
