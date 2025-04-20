package com.petmatz.api.auth.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@Setter
public class RepasswordRequest {
    private String currentPassword;
    private String newPassword;
}
