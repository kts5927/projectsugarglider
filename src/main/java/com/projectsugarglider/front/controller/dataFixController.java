package com.projectsugarglider.front.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.projectsugarglider.front.api.KakaoLocationFix;
import com.projectsugarglider.front.dto.KakaoPlace;
import com.projectsugarglider.front.service.LocationDataFix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/Api")
public class dataFixController {

    private final KakaoLocationFix kakaoApi;
    private final LocationDataFix fix;

    @GetMapping("/kakao")
    @ResponseBody
    public List<KakaoPlace> req(@RequestParam String store){
        return kakaoApi.localDataCall(store);
    }

    @GetMapping("/locationFix")
    public ResponseEntity<String> req(){
        fix.service();
        return ResponseEntity.ok("데이터 픽스 완료");
    }
    
}
