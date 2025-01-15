package com.petmatz.domain.user.response;

import com.petmatz.domain.user.entity.KakaoRegion;
import lombok.Data;

import java.util.List;

@Data
public class KakaoGeocodingResponse {
    private List<KakaoRegion> documents;
}
