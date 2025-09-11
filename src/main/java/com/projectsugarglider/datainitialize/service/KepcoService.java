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

/**
 * KEPCO(한전) 지역 데이터 저장용 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KepcoService {

    private final KepcoAPI kepcoAPI;
    private final UpperLocationCodeRepository upperRepo;
    private final LowerLocationCodeRepository lowerRepo;
    private final ApiNameFix nameFix;

    /**
     * 한전 지역 데이터를 업데이트 합니다.
     */
    @Transactional
    public void updateBaseKepcoData() {

        List<KepcoCommonDto> list = kepcoAPI.baseDataCall();

        for (KepcoCommonDto dto : list) {
            String upperKey  = nameFix.fixUpper(dto.uppoCdNm()); 
            String lowerKey  = nameFix.fixLower(dto.codeNm());   
            String uppocode  = dto.uppoCd();
            String lowercode = dto.code();  

            upperRepo.updateKepcoCodeByKey(upperKey, uppocode);
            lowerRepo.updateKepcoCodeByKey(upperKey, lowerKey, lowercode);
        }
    }
}
