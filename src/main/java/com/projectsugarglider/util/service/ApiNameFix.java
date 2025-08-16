package com.projectsugarglider.util.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ApiNameFix {

    // 시도명
    private static final Map<String, String> RENAMES = Map.of(
        "강원도",   "강원특별자치도",
        "전라북도", "전북특별자치도"
    );

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
        return RENAMES.getOrDefault(originalUppo, originalUppo);
    }

    /**
     * 하위(구/군) 지역명 공백 제거
     * @param originalLower 원본 구/군명
     * @return 공백이 모두 제거된 이름
     */
    public static String fixLower(String originalLower) {
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
