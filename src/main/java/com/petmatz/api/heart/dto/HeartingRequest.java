package com.petmatz.api.heart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeartingRequest {
    @NotBlank
    private Long heartedId;
}
