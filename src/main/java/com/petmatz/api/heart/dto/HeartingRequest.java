package com.petmatz.api.heart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeartingRequest {
    @NotBlank
    @Schema(description = "찜할 상대방의 ID", example = "2")
    private Long heartedId;
}
