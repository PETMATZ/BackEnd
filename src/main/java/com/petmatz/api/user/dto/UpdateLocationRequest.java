package com.petmatz.api.user.dto;

import garbege.service.user.info.UpdateLocationInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateLocationRequest {
    @NotBlank
    private final Double latitude;

    @NotBlank
    private final Double longitude;

    public static UpdateLocationInfo of(UpdateLocationRequest reqDto) {
        return UpdateLocationInfo.builder()
                .latitude(reqDto.getLatitude())
                .longitude(reqDto.getLongitude())
                .build();
    }
}
