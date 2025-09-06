package com.projectsugarglider.kca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projectsugarglider.front.dto.KcaStoreInfoResponseDto;
import com.projectsugarglider.kca.repository.StoreInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KcaStoreInfoResponseService {
    
    private final StoreInfoRepository repo;

    public List<KcaStoreInfoResponseDto> service(String upper, String lower){
        return repo.findByUpperCodeAndLowerCode(upper, lower);
    }
}
