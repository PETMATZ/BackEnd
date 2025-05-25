package com.petmatz.application.user.validator;

import com.petmatz.domain.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.petmatz.domain.user.exception.UserErrorCode.PASS_WORD_MISMATCH;

@Component
@RequiredArgsConstructor
public class PasswordValidator {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String repasswordNum) {
        passwordEncoder.encode(repasswordNum);
        return repasswordNum;
    }

    public void validatePassword(String currentPassword, String userPassword) {
        if (!passwordEncoder.matches(currentPassword, userPassword)) {
            throw new UserException(PASS_WORD_MISMATCH);
        }
    }
}