package com.petmatz.infra.adapter.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoRegion {
    @JsonProperty("region_1depth_name")
    private String region1; // 예: 서울
    @JsonProperty("region_2depth_name")
    private String region2; // 예: 강남구 역삼동
    @JsonProperty("region_3depth_name")
    private String region3; // 예: 역삼동
    @JsonProperty("code")
    private String code;    // 행정구역 코드 (String 타입)

    /**
     * '구'까지만 반환하는 메서드
     *
     * @return "서울 강남구"와 같은 형식
     */
    public String getRegionName() {
        // '구'까지만 표시되도록 region2를 공백 기준으로 split 후 첫 번째 단어 반환
        String region2Trimmed = region2.contains(" ") ? region2.split(" ")[0] : region2;
        return region1 + " " + region2Trimmed;
    }

    public Integer getCodeAsInteger() {
        try {
            String trimmedCode = code.length() > 6 ? code.substring(0, 6) : code;
            return Integer.parseInt(trimmedCode);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse region code: " + code);
            return null;
        }
    }
}
