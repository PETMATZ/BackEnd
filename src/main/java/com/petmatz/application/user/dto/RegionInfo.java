package com.petmatz.application.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegionInfo(
        String region1, // 예: 서울
        String region2, // 예: 강남구 역삼동
        String region3, // 예: 역삼동
        Integer code
        ) {

    public String getRegion() {
        StringBuilder st = new StringBuilder();
        st.append(region1).append(" ").append(region2).append(" ").append(region3);
        return st.toString();
    }

}
