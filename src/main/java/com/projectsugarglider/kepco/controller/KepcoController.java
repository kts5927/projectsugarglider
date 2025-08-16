package com.projectsugarglider.kepco.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectsugarglider.kepco.dto.KepcoUsageDto;
import com.projectsugarglider.kepco.service.UsageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Kepco")
@RequiredArgsConstructor
public class KepcoController {
    
    private final UsageService usageService;

    @PostMapping("/UsageDataInsert")
    public ResponseEntity<String> usageDataUpdate(){
        usageService.insertUsageKepcoData();
        return ResponseEntity.ok("전력사용량 데이터 업데이트 성공");
    }


    @GetMapping("/last-year-same-month")
    public List<KepcoUsageDto> lastYearSameMonth() {
        return usageService.getLastYearSameMonthUsage();
    }
}
