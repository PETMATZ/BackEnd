package com.petmatz.infra.adapter.kakao.dto;

import lombok.Data;

import java.util.List;

@Data
public class KakaoGeocodingResponse {
    private List<KakaoRegion> documents;
}
