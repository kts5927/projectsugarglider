package com.projectsugarglider.datainitialize.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.projectsugarglider.datainitialize.api.KcaAPI;
import com.projectsugarglider.datainitialize.dto.KcaCommonDto;
import com.projectsugarglider.datainitialize.repository.LowerLocationCodeRepository;
import com.projectsugarglider.datainitialize.repository.UpperLocationCodeRepository;
import com.projectsugarglider.util.service.ApiNameFix;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KcaService {

    private final KcaAPI kcaAPI;
    private final UpperLocationCodeRepository upperRepo;
    private final LowerLocationCodeRepository lowerRepo;
    private final ApiNameFix nameFix;

    /**
     * 소비자원 지역 데이터를 업데이트합니다.
     * 하위 지역 데이터를 저장할때 상위 지역코드가 필요한데,
     * 상위지역 코드를 저장할떄 upperNameByKca에 미리 저장해놓아서
     * Hash코드로 하위코드가 상위코드를 빠르게 조회할 수 있게 도와줍니다.
     */
    @Transactional
    public void updateBaseKcaData() {

        List<KcaCommonDto> list = kcaAPI.baseDataCall();
        String upperName = "default";
        Map<String, String> upperNameByKca = new HashMap<>();

        /**
         * dto.codeName은 상위/하위 모두 지역값만 들어옵니다. (예시/ 서울특별시 , 관악구 ..)
         * 따라서 데이터를 판단할때 상위/하위 지역코드가 같은 형식으로 들어오기 때문에
         * code를 upper/lower로 나누어서 이 레코드가 상위/하위 데이터중 어떤것인지 판별합니다.
         */
        for (KcaCommonDto dto : list) {
            String code  = dto.code(); 
            String codeName = dto.codeName();
            String highCode = dto.highCode();

            // ex)0201/00000 - 서울특별시
            String upperKcaCode = code.substring(0, 4);
            String lowerKcaCode = code.substring(4, 9);
            
            /**
             * 만약 lowerKcaCode가 "00000"이라면 상위코드이고, 
             * "00000"이 아니라면 lowerCode입니다.
             * ex) 020100000
             * upperKcaCode = 0201
             * lowerKcaCode = 00000
             * 지역명 = 서울특별시(upperCode)
             */
            if ("00000".equals(lowerKcaCode)) {

                codeName = nameFix.fixUpper(codeName);
                upperName = codeName;
                upperRepo.updateKcaCodeByKey(codeName, upperKcaCode);
                upperNameByKca.put(upperKcaCode, upperName);
            
            } else {

                codeName = nameFix.fixLower(codeName);
                String parentUpperKca = highCode.substring(0, 4);
                String resolvedUpperName = upperNameByKca.getOrDefault(parentUpperKca, upperName);
                lowerRepo.updateKcaCodeByKey(resolvedUpperName, codeName, lowerKcaCode);

            }
        }
    }
}
