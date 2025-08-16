package com.projectsugarglider;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.projectsugarglider.datainitialize.repository.UpperLocationCodeRepository;
import com.projectsugarglider.kepco.entity.KepcoUsageEntity;
import com.projectsugarglider.kepco.service.UsageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class UsageApiTest {
    
    @Autowired
    private UsageService usageService;

    @Autowired
    private UpperLocationCodeRepository upperLocationCodeRepository;

    @Test
    void kepcoRepositoryTest(){
        log.info("logdata : {}", upperLocationCodeRepository.findAllAdministrativeCodes());
    }
    
    @Test
    void insertKepcoTest(){
        List<String> mok = usageService.fetchAllKepcoCodes();
        log.info("위치데이터 : {}",mok);
        List<KepcoUsageEntity> mokk = usageService.buildUsageEntities("2024", "07", mok);
        log.info("데이터반환값 : {}",mokk);
    }

}
