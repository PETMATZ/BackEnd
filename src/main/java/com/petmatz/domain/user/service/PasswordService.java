package com.petmatz.domain.user.service;

import com.petmatz.api.user.request.SendRepasswordRequestDto;
import com.petmatz.common.security.jwt.JwtExtractProvider;
import com.petmatz.domain.user.component.PasswordComponent;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.RepasswordInfo;
import com.petmatz.domain.user.provider.RePasswordProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordService {

    private final RePasswordEmailProvider rePasswordEmailProvider;
    private final JwtExtractProvider jwtExtractProvider;
    private final UserUtils userUtils;
    private final PasswordComponent passwordComponent;

    @Transactional
    public void sendRepassword(SendRepasswordRequestDto dto) {
        String accountId = dto.getAccountId();
        User user = userUtils.findUser(accountId);

        String rePasswordNum = RePasswordProvider.generatePassword();

        rePasswordEmailProvider.sendVerificationEmail(accountId, rePasswordNum);
        String encodedRePasswordNum = passwordComponent.encodePassword(rePasswordNum);
        user.updatePassword(encodedRePasswordNum);
    }

    @Transactional
    public void repassword(RepasswordInfo info) {
        Long userId = jwtExtractProvider.findIdFromJwt();
        User user = userUtils.findIdUser(userId);

        String currentPassword = info.getCurrentPassword();
        passwordComponent.validatePassword(currentPassword, user.getPassword());

        String newPassword = info.getNewPassword();

        String encodedNewPassword = passwordComponent.encodePassword(newPassword);
        user.updatePassword(encodedNewPassword);
    }
}
