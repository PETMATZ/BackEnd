package com.petmatz.api.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "위치 업데이트 DTO")
public class UpdateLocationRequest {

    @NotBlank
    @Schema(description = "위도 값", example = "37.5665")
    private final Double latitude;

    @NotBlank
    @Schema(description = "경도 값", example = "126.978")
    private final Double longitude;
}
