package com.projectsugarglider.kca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projectsugarglider.kca.service.KcaDummy;
import com.projectsugarglider.kca.service.KcaNullData;
import com.projectsugarglider.kca.service.KcaPriceService;
import com.projectsugarglider.kca.service.KcaProductInfoService;
import com.projectsugarglider.kca.service.KcaStandardDataSaveService;
import com.projectsugarglider.kca.service.KcaStoreInfoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/kca")
@RequiredArgsConstructor
public class KcaController {

    private final KcaStandardDataSaveService KcaService;
    private final KcaStoreInfoService InfoService;
    private final KcaDummy dummy;
    private final KcaNullData nullData;
    private final KcaProductInfoService priceInfo;
    private final KcaPriceService priceservice;

    @PostMapping("/1")
    public ResponseEntity<String> DataUpdateOne() throws JsonProcessingException{
        KcaService.saveUnitData();
        KcaService.saveEntpData();
        dummy.insertDummy();
        KcaService.saveTotalData();
        nullData.insertData();
        InfoService.saveStoreInfoData();
        return ResponseEntity.ok("kca 데이터 업데이트 성공");
    }

    @PostMapping("/2")
    public ResponseEntity<String> DataUpdateTwo() throws JsonProcessingException{
        priceInfo.SaveProductInfoData();
        return ResponseEntity.ok("kca 데이터 업데이트 성공");
    }

    @PostMapping("/3")
    public ResponseEntity<String> DataUpdateThree() throws JsonProcessingException{
        priceservice.SavePriceInfoData();
        return ResponseEntity.ok("kca 데이터 업데이트 성공");
    }

}
