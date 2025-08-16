package com.projectsugarglider.kepco.service;

import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.projectsugarglider.datainitialize.repository.UpperLocationCodeRepository;
import com.projectsugarglider.kepco.api.UsageDataApi;
import com.projectsugarglider.kepco.dto.KepcoUsageDto;
import com.projectsugarglider.kepco.entity.KepcoUsageEntity;
import com.projectsugarglider.kepco.repository.KepcoUsageRepository;
import com.projectsugarglider.util.service.ApiNameFix;
import com.projectsugarglider.util.service.DateTime;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsageService {

    private final UpperLocationCodeRepository upperLocationCodeRepository;
    private final KepcoUsageRepository          usageRepository;
    private final UsageDataApi                  usageDataApi;
    private final DateTime                      dateTime;
    private final ApiNameFix                    nameFix;

    @Transactional
    public void insertUsageKepcoData() {
        
        OffsetDateTime nowUtc = dateTime.utcTime();
        String year  = String.valueOf(dateTime.lastYear(nowUtc));
        String month = String.format("%02d", dateTime.timeMonth(nowUtc));

        List<String> metroCdRaw    = fetchAllKepcoCodes();
        List<KepcoUsageEntity> es  = buildUsageEntities(year, month, metroCdRaw);

        usageRepository.saveAll(es);
    }

    public List<String> fetchAllKepcoCodes() {
        return upperLocationCodeRepository.findAllAdministrativeCodes();
    }

    public List<KepcoUsageEntity> buildUsageEntities(
            String year, String month, List<String> metroCodes
    ) {
        return metroCodes.stream()
            .flatMap(metroCd ->
                usageDataApi.usageDataCall(year, month, metroCd).stream())
            .map(dto -> dto.toUsageEntity(year, month, nameFix))
            .toList();
    }
    @Cacheable(
      value = "lastYearUsage",
      key = "'KepcoUsageCall'"
    )
    public List<KepcoUsageDto> getLastYearSameMonthUsage() {
        YearMonth last = YearMonth.now().minusYears(1);
        String y = String.valueOf(last.getYear());
        String m = String.format("%02d", last.getMonthValue());

        return usageRepository.findByYearAndMonth(y, m).stream()
            .map(KepcoUsageDto::from)
            .toList();
    }
}