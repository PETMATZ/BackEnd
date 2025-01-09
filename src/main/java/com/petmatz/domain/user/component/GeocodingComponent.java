package com.petmatz.domain.user.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.petmatz.domain.user.entity.KakaoRegion;
import com.petmatz.domain.user.exception.UserException;
import com.petmatz.domain.user.response.KakaoGeocodingResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.petmatz.domain.user.entity.KakaoRegion;


import java.util.List;

import static com.petmatz.domain.user.exception.MatchErrorCode.INSUFFICIENT_LOCATION_DATA;

@Service
public class GeocodingComponent {
    @Value("${kakao-api-url}")
    private String KAKAO_API_URL;
    @Value("${kakao-api-key}")
    private String KAKAO_API_KEY;

    public KakaoRegion getRegionFromCoordinates(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String url = KAKAO_API_URL + "?x=" + longitude + "&y=" + latitude;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<KakaoGeocodingResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, KakaoGeocodingResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<KakaoRegion> regions = response.getBody().getDocuments();

                if (!regions.isEmpty()) {
                    KakaoRegion region = regions.get(0);
                    logInfo(region);
                    return region;
                } else {
                    System.err.println("No region data found for coordinates: " + latitude + ", " + longitude);
                }
            } else {
                System.err.println("Kakao API returned unexpected status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error while fetching region data: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private void logInfo(KakaoRegion region) {
        System.out.println("Region Name: " + region.getRegionName());
        System.out.println("Region Code: " + region.getCode());
    }

    /**
     * 좌표를 기반으로 지역 정보를 가져오고 유효성을 검증
     */
    public KakaoRegion getValidRegion(double latitude, double longitude) {
        KakaoRegion kakaoRegion = getRegionFromCoordinates(latitude, longitude);
        // 지역 정보 검증
        if (kakaoRegion == null || kakaoRegion.getCodeAsInteger() == null) {
            throw new UserException(INSUFFICIENT_LOCATION_DATA);
        }
        return kakaoRegion;
    }
}