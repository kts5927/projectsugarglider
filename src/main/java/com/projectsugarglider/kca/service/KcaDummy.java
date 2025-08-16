package com.projectsugarglider.kca.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectsugarglider.kca.entity.KcaGoodTotalDivCodeEntity;
import com.projectsugarglider.kca.repository.TotalDivRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KcaDummy {

    private final TotalDivRepository repository;

    /**
     * 더미 데이터 주입:
     * (030300000, 신선식품, null)
     * (030304000, 더미, 030300000)
     */
    @Transactional
    public void insertDummy() {

        // 1) 부모(신선식품)
        if (!repository.existsById("030300000")) {
            KcaGoodTotalDivCodeEntity parent = KcaGoodTotalDivCodeEntity.builder()
                .code("030300000")
                .codeName("신선식품")
                .highCode(null)              
                .build();
            repository.save(parent);
            log.info("insert parent: {}", parent.getCode());
        } else {
            log.info("parent already exists: 030300000");
        }

        // 2) 자식(더미) → highCode 로 부모 연계
        if (!repository.existsById("030304000")) {
            KcaGoodTotalDivCodeEntity child = KcaGoodTotalDivCodeEntity.builder()
                .code("030304000")
                .codeName("더미")
                .highCode("030300000")       // 부모 코드 지정 (parent 필드는 insertable=false 이므로 무시)
                .build();
            repository.save(child);
            log.info("insert child: {} -> highCode={}", child.getCode(), child.getHighCode());
        } else {
            log.info("child already exists: 030304000");
        }
    }
}
