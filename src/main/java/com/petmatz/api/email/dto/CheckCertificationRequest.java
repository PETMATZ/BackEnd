package com.petmatz.api.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckCertificationRequest {
    @Email
    @NotBlank
    @Schema(description = "사용자 이메일", example = "test@gmail.com")
    private final String accountId;

    @NotBlank
    @Schema(description = "인증번호", example = "1qw2134fq")
    private final String certificationNumber;

}
