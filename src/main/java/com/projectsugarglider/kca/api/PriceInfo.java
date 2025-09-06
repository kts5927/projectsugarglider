package com.projectsugarglider.kca.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projectsugarglider.apiconnect.GenericExternalApiService;
import com.projectsugarglider.kca.dto.KcaPriceInfoDto;
import com.projectsugarglider.util.service.DateTime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceInfo {
    
    @Value("${external.publicapi.apiKey}")
    private String apiKey;

        
    @Value("${external.kca.priceinfo}")
    private String BASE_URL;

    private final GenericExternalApiService apiService;
    private final DateTime dateTime;


    public List<KcaPriceInfoDto> priceEntpCall(
        String entpId
    ){
        return priceInfoCall(entpId,null);
    }

    public List<KcaPriceInfoDto> priceGoodCall(
        String goodId
    ){
        return priceInfoCall(null, goodId);
    }

    public List<KcaPriceInfoDto> priceInfoCall(

        String entpId, 
        String goodId

    ){

        Map<String, String> params = getParams(entpId, goodId);

        return apiService.getCall(
            BASE_URL,
            params,
            KcaPriceInfoDto[].class,
            true,
            "/result/iros.openapi.service.vo.goodPriceVO"
            );
    }


    /*
     * 파라미터를 만드는 함수입니다.
     * entpId/goodId 둘 중 하나만 들어왔는지도 확인합니다.
     */
    private Map<String, String> getParams(String entpId, String goodId) {
        boolean hasEntp = entpId != null && !entpId.isBlank();
        Map<String, String> params = new LinkedHashMap<>();

        params.put("serviceKey", apiKey);
        if (hasEntp) {
            params.put("entpId", entpId);
        } else {
            params.put("goodId", goodId);
        }

        params.put("goodInspectDay",dateTime.previousTwoWeekFridayYyyyMmDd());

        return params;
    }

}