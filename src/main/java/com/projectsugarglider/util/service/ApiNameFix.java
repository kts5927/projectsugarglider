package com.projectsugarglider.util.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ApiNameFix {

    // 시도명
    private static final Map<String, String> RENAMEUPPO = Map.of(
        "강원도",   "강원특별자치도",
        "전라북도", "전북특별자치도"
    );

    public static final Map<String, String> RENAMEUPPOSIMPLY;
    static {
        Map<String, String> m = new HashMap<>();
        m.put("서울특별시", "서울");
        m.put("부산광역시", "부산");
        m.put("대구광역시", "대구");
        m.put("인천광역시", "인천");
        m.put("광주광역시", "광주");
        m.put("대전광역시", "대전");
        m.put("울산광역시", "울산");
        m.put("경기도", "경기");
        m.put("강원", "강원특별자치도");
        m.put("강원도", "강원특별자치도");
        m.put("충청북도", "충북");
        m.put("충청남도", "충남");
        m.put("전라북도", "전북특별자치도");
        m.put("전북", "전북특별자치도");
        m.put("전라남도", "전남");
        m.put("경상북도", "경북");
        m.put("경상남도", "경남");
        m.put("제주특별자치도", "제주");
        m.put("세종", "세종특별자치시");
        RENAMEUPPOSIMPLY = Collections.unmodifiableMap(m);
    }
    /**
     * 상위(시도) 지역명 보정
     * @param originalUppo 원본 시도명
     * @return 매핑 정보가 있으면 바뀐 이름, 없으면 그대로
     */
    public String fixUpper(String originalUppo) {
        if (originalUppo == null) {
            // null 일 때 기본값 반환하거나 예외 처리
            return originalUppo;
        }
        return RENAMEUPPO.getOrDefault(originalUppo, originalUppo);
    }

    public static String fixUpperSimply(String originalUppo){
        return RENAMEUPPOSIMPLY.getOrDefault(originalUppo, originalUppo);
    }


    /**
     * 하위(구/군) 지역명 공백 제거
     * @param originalLower 원본 구/군명
     * @return 공백이 모두 제거된 이름
     */
    public String fixLower(String originalLower) {
        if (originalLower == null) {
            return null;
        }

        if ("세종시".equals(originalLower)){
            return "세종특별자치";
        }

        if ("부산".equals(originalLower)){
            return "부산진";
        }

        if ("해운".equals(originalLower)){
            return "해운대";
        }

        // 문자열 내 모든 공백(\s)을 제거
        String noSpace = originalLower.replaceAll("\s+", "");
        return noSpace.replaceFirst("(시|군|구)$", "");    
    }


}
