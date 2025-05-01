package com.petmatz.api.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class SignInRequest {
    @Email
    @NotBlank
    @Schema(description = "이메일", example = "test@gmail.com")
    private final String accountId;

    @NotBlank
    @Schema(description = "비밀번호", example = "123456")
    private final String password;

}
