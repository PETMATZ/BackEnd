package com.petmatz.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignInRequest {
    @Email
    @NotBlank
    private final String accountId;

    @NotBlank
    private final String password;

}
