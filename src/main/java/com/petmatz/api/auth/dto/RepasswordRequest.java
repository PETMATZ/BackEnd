package com.petmatz.api.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class RepasswordRequest {

    @Schema(description = "현재 비밀번호")
    private final String currentPassword;
    @Schema(description = "새 비밀번호")
    private final String newPassword;
}
