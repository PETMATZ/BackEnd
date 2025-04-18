package com.petmatz.infra.adapter.kakao;

import com.petmatz.application.user.dto.RegionInfo;
import com.petmatz.domain.user.port.GeocodingPort;
import com.petmatz.infra.adapter.kakao.dto.KakaoGeocodingResponse;
import com.petmatz.infra.adapter.kakao.dto.KakaoRegion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class KakaoGeocodingAdapter implements GeocodingPort {
    @Value("${kakao-api-url}")
    private String KAKAO_API_URL;
    @Value("${kakao-api-key}")
    private String KAKAO_API_KEY;

    @Override
    public RegionInfo getRegion(double latitude, double longitude) {
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
                    return new RegionInfo(regions.get(0).getRegion1(), regions.get(0).getRegion2(), regions.get(0).getRegion3(), Integer.parseInt(regions.get(0).getCode()));
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

//    /**
//     * 좌표를 기반으로 지역 정보를 가져오고 유효성을 검증
//     */
//    public KakaoRegion getValidRegion(double latitude, double longitude) {
//        KakaoRegion kakaoRegion = getRegionFromCoordinates(latitude, longitude);
//        // 지역 정보 검증
//        if (kakaoRegion == null || kakaoRegion.getCodeAsInteger() == null) {
//            throw new UserException(INSUFFICIENT_LOCATION_DATA);
//        }
//        return kakaoRegion;
//    }

}
