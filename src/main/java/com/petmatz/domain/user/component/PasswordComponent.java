package com.petmatz.domain.user.component;

import com.petmatz.domain.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.petmatz.domain.user.exception.MatchErrorCode.PASSWORD_MISMATCH;

@Component
@RequiredArgsConstructor
public class PasswordComponent {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String repasswordNum) {
        passwordEncoder.encode(repasswordNum);
        return repasswordNum;
    }

    public void validatePassword(String currentPassword, String userPassword) {
        if (!passwordEncoder.matches(currentPassword, userPassword)) {
            throw new UserException(PASSWORD_MISMATCH);
        }
    }
}