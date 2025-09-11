package com.projectsugarglider.kca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projectsugarglider.kca.service.KcaLocationData;
import com.projectsugarglider.kca.service.KcaProductInfoService;
import com.projectsugarglider.kca.service.KcaStandardDataSaveService;
import com.projectsugarglider.kca.service.KcaStoreInfoSaveService;

import lombok.RequiredArgsConstructor;

/**
 * KCA(소비자원) 기본 컨트롤러.
 */
@RestController
@RequestMapping("/kca")
@RequiredArgsConstructor
public class KcaController {

    private final KcaStandardDataSaveService KcaService;
    private final KcaStoreInfoSaveService InfoService;
    private final KcaLocationData nullData;
    private final KcaProductInfoService priceInfo;

    /**
     * 소비자원 기본데이터를 업데이트합니다.
     * 포함된 내용은 다음과 같습니다.
     * 
     * 1. 상품 단위데이터
     * 2. 업체 데이터
     * 3. 상품 소분류 코드
     * 4. 수정된 소비자원 지역코드 (기상청 데이터와 )
     * 5. 업체 정보 데이터
     * 6. 상품정보 데이터
     * 
     * @return 데이터 저장 완료 메시지
     * @throws JsonProcessingException
     */
    @PostMapping("/BasicDataUpdate")
    public ResponseEntity<String> DataUpdateOne() throws JsonProcessingException{
        KcaService.saveUnitData();
        KcaService.saveEntpData();
        KcaService.saveTotalData();
        nullData.insertData();
        InfoService.saveStoreInfoData();
        priceInfo.SaveProductInfoData();
        return ResponseEntity.ok("kca 데이터 업데이트 성공");
    }


}
