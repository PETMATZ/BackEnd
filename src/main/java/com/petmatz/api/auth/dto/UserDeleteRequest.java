package com.petmatz.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UserDeleteRequest {
    @NotBlank
    @Schema(description = "비밀번호", example = "123456")
    private final String password;
}