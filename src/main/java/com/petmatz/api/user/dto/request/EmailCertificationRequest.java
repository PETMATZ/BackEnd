package com.petmatz.api.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class EmailCertificationRequest {
    @Email
    @NotBlank
    @Schema(description = "사용자 이메일", example = "test@gmail.com")
    private final String accountId;
}
