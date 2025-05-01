package com.petmatz.application.user.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationValidator {
    /**
     * 필수 정보 누락 확인
     */
    public void validateRequiredFields(String accountId, String certificationNumber, String password) {
        if (accountId == null || certificationNumber == null || password == null) {
            throw new IllegalArgumentException("필수 정보가 누락되었습니다.");
        }
    }
}
