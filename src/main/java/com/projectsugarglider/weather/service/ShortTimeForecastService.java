package com.projectsugarglider.weather.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectsugarglider.util.dto.LocationDto;
import com.projectsugarglider.util.service.DateTime;
import com.projectsugarglider.weather.api.ShortTimeForecastApi;
import com.projectsugarglider.weather.dto.ShortTimeForecastDto;
import com.projectsugarglider.weather.entity.ShortTimeForecastEntity;
import com.projectsugarglider.weather.repository.ShortTimeForecastRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortTimeForecastService {

    private final ShortTimeForecastApi         api;
    private final ShortTimeForecastRepository  repo;
    private final DateTime                     dateTime;
    private static final DateTimeFormatter     YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 주어진 위치에 대해 “오늘 기준 +4일 00시” 데이터가 없다면
     * 누락된 부분만 API로 채워서 저장한다.
     */
    @Transactional
    public void saveAllShortTimeForecast(LocationDto loc) {
        // 1. KST 현재 시각, +4일 00:00 타겟
        OffsetDateTime now     = dateTime.kstNow();
        OffsetDateTime target  = dateTime.kstPlusDays(4)
                                    .withHour(0).withMinute(0).withSecond(0).withNano(0);

        // 2. 타겟 시각 데이터가 이미 있으면 스킵
        if (existsAt(target, loc)) {
            return;
        }

        Set<String> existing = new HashSet<>(
            repo.findAllTimeKeysByLocation(loc.lowerCode(), loc.upperCode()));


        // 3. 지금까지 저장된 마지막 시각 (없으면 오늘 05:00)
        OffsetDateTime lastSaved = findLastSaved(loc)
            .orElseGet(() -> now.withHour(5).withMinute(0).withSecond(0).withNano(0));

        // 4. API 호출 → DTO 리스트
        String todayParam = now.format(YYYYMMDD);
        List<ShortTimeForecastDto> dtos = api.forecastCall(loc.nx(), loc.ny(), todayParam);

        // 5. DTO 중에서 “(lastSaved, target]” 구간만 골라서 Entity로 변환
        List<ShortTimeForecastEntity> toSave = dtos.stream()
            .filter(dto -> {
                String key = dto.fcstDate() + dto.fcstTime();
                return !existing.contains(key);
            })
            .map(dto -> dto.toEntity(dto, loc))
            .toList();

        // 6. 저장
        if (!toSave.isEmpty()) {
            repo.saveAll(toSave);
            log.info("▶ 누락 보충 저장: {}건 (loc={}, from={} to={})",
                toSave.size(), loc, lastSaved, target);
        } else {
            log.info("▶ 저장할 누락 데이터 없음 (loc={})", loc);
        }
    }

    /** target 시각에 이미 레코드가 있는지 */
    private boolean existsAt(OffsetDateTime target, LocationDto loc) {
        String[] p = dateTime.toYYYYMMDDHHMM(target);
        boolean ex = repo.existsByYearAndMonthAndDayAndTimeAndLowerCodeAndUpperCode(
            p[0], p[1], p[2], p[3], loc.lowerCode(), loc.upperCode());
        if (ex) {
            log.info("▶ {}-{}-{} {} 이미 저장됨, 스킵", (Object[])p);
        }
        return ex;
    }

    /** 저장된 엔티티 중 가장 최근 시각을 가져와 OffsetDateTime으로 파싱 */
    private Optional<OffsetDateTime> findLastSaved(LocationDto loc) {
        return repo
            .findTopByLowerCodeAndUpperCodeOrderByYearDescMonthDescDayDescTimeDesc(
                loc.lowerCode(), loc.upperCode()
            )
            .map(e -> dateTime.parseKst(
                e.getYear() + e.getMonth() + e.getDay(),
                e.getTime()
            ));
    }
}
