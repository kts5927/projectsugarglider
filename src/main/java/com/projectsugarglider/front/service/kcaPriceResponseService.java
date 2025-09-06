package com.projectsugarglider.front.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.projectsugarglider.front.dto.KcaPriceResponseDto;
import com.projectsugarglider.kca.entity.KcaPriceInfoEntity;
import com.projectsugarglider.kca.entity.KcaProductInfoEntity;
import com.projectsugarglider.kca.entity.KcaStoreInfoEntity;
import com.projectsugarglider.kca.repository.KcaPriceInfoRepository;
import com.projectsugarglider.kca.repository.ProductInfoRepository;
import com.projectsugarglider.kca.repository.StoreInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class kcaPriceResponseService {

    private final ProductInfoRepository productInfo;
    private final StoreInfoRepository storeInfo;
    private final KcaPriceInfoRepository priceInfo;
    
    @Cacheable(cacheNames = "kcaProductNameHash", key = "'all'", sync = true)
    public Map<String, String> productNameHash() {
        return productInfo.findAll().stream()
                .collect(Collectors.toMap(
                        KcaProductInfoEntity::getGoodId,
                        KcaProductInfoEntity::getGoodName,
                        (a, b) -> a,
                        HashMap::new
                ));
    }

    @Cacheable(cacheNames = "kcaStoreHashById", key = "'all'", sync = true)
    public Map<String, KcaStoreInfoEntity> StoreHashById() {
        return storeInfo.findAll().stream()
                .collect(Collectors.toMap(
                        KcaStoreInfoEntity::getEntpId,
                        Function.identity(),
                        (a, b) -> a,     
                        HashMap::new
                ));
    }

        public List<KcaPriceResponseDto> listByEntpId(String entpId) {
        List<KcaPriceInfoEntity> rows = priceInfo.findByEntpId(entpId);
        Map<String, String> nameMap = productNameHash();

        return rows.stream()
                .map(e -> new KcaPriceResponseDto(
                        nameMap.getOrDefault(e.getGoodId(), e.getGoodId()),                 // good_name
                        e.getGoodPrice() == null ? null : String.valueOf(e.getGoodPrice()), // good_price
                        e.getPlusoneYn(),                                                   // plusone_yn
                        formatDiscountPeriod(e.getGoodDcStartDay(), e.getGoodDcEndDay())    // date
                ))
                .sorted(Comparator.comparing(KcaPriceResponseDto::good_name,
                        Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }

    

    private String formatDiscountPeriod(String goodDcStartDay, String goodDcEndDay) {
        String s = toMmDd(goodDcStartDay);
        String e = toMmDd(goodDcEndDay);
    
        if (s == null && e == null) return "-";        // 둘 다 없으면 '-'
        if (s == null) s = "-";                        // 하나만 있으면 반대편은 '-'
        if (e == null) e = "-";
    
        return s + " ~ " + e;                          // "MM월DD일 ~ MM월DD일"
    }

    private String toMmDd(String yyyymmdd) {
        if (yyyymmdd == null) return null;
        String v = yyyymmdd.trim();
        if (v.length() != 8) return null;              // 형식 깨지면 null
        String mm = v.substring(4, 6);
        String dd = v.substring(6, 8);
        return mm + "월" + dd + "일";
    }
}