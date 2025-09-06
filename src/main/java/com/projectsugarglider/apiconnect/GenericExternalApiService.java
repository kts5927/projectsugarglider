package com.projectsugarglider.apiconnect;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenericExternalApiService {

    //TODO : 로깅 원위치
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    /**
     * 외부 API 호출을 범용으로 처리합니다. (헤더 없이)
     *
     * @param baseUrl       호출할 엔드포인트 URL
     * @param queryParams   쿼리 파라미터 맵 (key → value)
     * @param responseType  결과를 매핑할 DTO 배열 클래스 (예: KcaCommonDto[].class)
     * @param isXml         응답이 XML이면 true, JSON이면 false
     * @param dataNodePath  JsonNode.at() 에 전달할 JSON 경로 (예: "/data" 또는 "/result/iros.openapi.service.vo.stdInfoVO")
     * @param <T>           DTO 타입
     * @return              DTO 리스트
     */
    public <T> List<T> getCall(
            String baseUrl,
            Map<String, String> queryParams,
            Class<T[]> responseType,
            boolean isXml,
            String dataNodePath
    ) {
        String url    = buildUrl(baseUrl, queryParams);
        // log.info("▶▶▶ calling URL = {}", url);

        String raw    = doRequest(url); // 기존 방식 유지
        JsonNode root = parseRaw(raw, isXml);
        JsonNode data = extractDataNode(root, dataNodePath);
        return convertToDto(data, responseType);
    }

    /**
     * 외부 API 호출을 범용으로 처리합니다. (커스텀 헤더 포함)
     *
     * @param baseUrl       호출할 엔드포인트 URL
     * @param queryParams   쿼리 파라미터 맵 (key → value)
     * @param responseType  결과를 매핑할 DTO 배열 클래스
     * @param isXml         응답이 XML이면 true, JSON이면 false
     * @param dataNodePath  JsonNode.at() 경로
     * @param headers       요청 헤더 맵 (예: Authorization 등)
     * @param <T>           DTO 타입
     * @return              DTO 리스트
     */
    public <T> List<T> getCall(
            String baseUrl,
            Map<String, String> queryParams,
            Class<T[]> responseType,
            boolean isXml,
            String dataNodePath,
            Map<String, String> headers
    ) {
        String url    = buildUrl(baseUrl, queryParams);
        // log.info("▶▶▶ calling URL = {}", url);

        String raw    = doRequest(url, headers); // 헤더 버전
        JsonNode root = parseRaw(raw, isXml);
        JsonNode data = extractDataNode(root, dataNodePath);
        return convertToDto(data, responseType);
    }

    // Url이 이미 인코딩 되어있다면 하지않음.
    private String buildUrl(String baseUrl, Map<String,String> qp) {
        String sk = qp.getOrDefault("serviceKey", qp.get("ServiceKey")); 
        boolean keyLooksEncoded = sk != null && sk.contains("%"); 
    
        UriComponentsBuilder b = UriComponentsBuilder.fromHttpUrl(baseUrl);
        qp.forEach(b::queryParam);
    
        if (keyLooksEncoded) {
            return b.build(true).toUriString();   // components already encoded
        } else {
            return b.encode(java.nio.charset.StandardCharsets.UTF_8)
                    .build()
                    .toUriString();               
        }
    }
    

    private String doRequest(String url) {
        // 헤더 없이 호출 (기존 호환)
        return doRequest(url, Collections.emptyMap());
    }

    private String doRequest(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::add);
        }
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return resp.getBody();
    }

    private JsonNode parseRaw(String raw, boolean isXml) throws UncheckedIOException {
        try {
            return isXml
                ? xmlMapper.readTree(raw)
                : objectMapper.readTree(raw);
        } catch (IOException e) {
            throw new UncheckedIOException("파싱 실패: " + raw, e);
        }
    }

    private JsonNode extractDataNode(JsonNode root, String path) {
        return root.at(path);
    }

    private <T> List<T> convertToDto(JsonNode data, Class<T[]> type) {
        T[] arr = objectMapper.convertValue(data, type);
        return arr != null ? List.of(arr) : Collections.emptyList();
    }
}
