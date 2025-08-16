package com.projectsugarglider.datainitialize.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projectsugarglider.datainitialize.service.BaseDataService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/DataInitialize")
@RequiredArgsConstructor
public class BaseDataController {

    private final BaseDataService allLocationDataSaveService;


    @PostMapping("/BasicDataUpdate")
    public ResponseEntity<String> basicDataUpdate() throws JsonProcessingException{
        allLocationDataSaveService.saveAllLocations();
        return ResponseEntity.ok("기본데이터 업데이트 성공");
    }

}
