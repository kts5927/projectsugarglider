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

    /**
     * 한전 지역 데이터를 업데이트 합니다.
     * 기상청 지역 코드와 같은지역이지만 다르게 표기된 이름이 있기 때문에
     * nameFix로 데이터를 보정해 줍니다.(예시/ 강원도 -> 강원특별자치도)
     */
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
