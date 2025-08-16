package com.projectsugarglider.util.service;

import org.springframework.stereotype.Service;

import com.projectsugarglider.datainitialize.repository.LowerLocationCodeRepository;
import com.projectsugarglider.util.dto.LocationDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeToLocationDto {
    private final LowerLocationCodeRepository repo;

    public LocationDto find(String upper, String lower) {
    return repo.findLocationWithCode(upper, lower);
  }
    
}
