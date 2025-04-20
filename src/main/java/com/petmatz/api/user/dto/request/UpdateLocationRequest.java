package com.petmatz.api.user.dto.request;

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
}
