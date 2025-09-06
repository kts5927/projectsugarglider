package com.projectsugarglider.front.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projectsugarglider.front.dto.KcaPriceResponseDto;
import com.projectsugarglider.front.dto.KcaStoreInfoResponseDto;
import com.projectsugarglider.front.dto.TemperatureResponseDto;
import com.projectsugarglider.front.service.KcaCallHistoryCheck;
import com.projectsugarglider.front.service.LocationDataInsert;
import com.projectsugarglider.front.service.WeatherCallHistoryCheck;
import com.projectsugarglider.front.service.kcaPriceResponseService;
import com.projectsugarglider.kca.service.KcaPriceService;
import com.projectsugarglider.kca.service.KcaStoreInfoResponseService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class frontController{

    private final LocationDataInsert location;
    private final KcaStoreInfoResponseService storeInfo;
    private final WeatherCallHistoryCheck check;
    private final KcaCallHistoryCheck kcaCheck;
    private final kcaPriceResponseService kca;
    private final KcaPriceService price;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class RegionPickRequest { private String upper; private String lower; }
    
    public record EntpReq(String entpId) {}

    @GetMapping("/")
    public String main(Model model) {

        return "main"; 
    }


    @GetMapping("/weather")
    public String dashboard(Model model) {

        String upper = "경기도";
        String lower = "가평";
    
        TemperatureResponseDto dto = check.service(upper, lower);
        model.addAttribute("labels", dto.labels());
        model.addAttribute("data", dto.data());
        model.addAttribute("datasetLabel", dto.datasetLabel());
    
        location.dataSave(model);
    
        return "weather"; 
    }

    @ResponseBody
    @PostMapping("/api/chart/tmp")
    public TemperatureResponseDto temperature(@RequestBody RegionPickRequest req){
        log.info("{},{}",req.upper,req.lower);
        return check.service(req.upper,req.lower);
    }

    @ResponseBody
    @PostMapping("/api/table/KcaPriceInfoByEntpId")
    public List<KcaPriceResponseDto> priceInfo(@RequestBody EntpReq req){
        log.info("{}",req);
        return kcaCheck.service(req.entpId);
    }

    @ResponseBody
    @PostMapping("/api/table/map")
    public List<KcaStoreInfoResponseDto> storeLocationInfo(@RequestBody RegionPickRequest req){
        log.info("{}",req);
        return storeInfo.service(req.upper, req.lower);
    }

    @GetMapping("/kca")
    public String kepco(Model model) {

        String upper = "경기도";
        String lower = "가평";

        TemperatureResponseDto dto = check.service(upper, lower);
        model.addAttribute("labels", dto.labels());
        model.addAttribute("data", dto.data());
        model.addAttribute("datasetLabel", dto.datasetLabel());
    
        location.dataSave(model);

        return "kca"; 
    }




}