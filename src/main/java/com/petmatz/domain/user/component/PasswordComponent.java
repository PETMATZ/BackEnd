package com.petmatz.domain.user.component;

import com.petmatz.api.user.request.SendRepasswordRequestDto;
import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.RepasswordInfo;
import com.petmatz.domain.user.provider.RePasswordProvider;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.GetMyProfileResponseDto;
import com.petmatz.domain.user.response.RepasswordResponseDto;
import com.petmatz.domain.user.response.SendRepasswordResponseDto;
import com.petmatz.domain.user.service.RePasswordEmailProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordComponent {

    private final UserRepository userRepository;
    private final RePasswordEmailProvider rePasswordEmailProvider;
    private final JwtExtractProvider jwtExtractProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseEntity<? super SendRepasswordResponseDto> sendRepassword(SendRepasswordRequestDto dto) {
        try {
            String accountId = dto.getAccountId();
            if (!userRepository.existsByAccountId(accountId)) {
                return GetMyProfileResponseDto.idNotFound();
            }
            User user = userRepository.findByAccountId(accountId);

            String rePasswordNum = RePasswordProvider.generatePassword();
            log.info("Generated Repassword: {}", rePasswordNum);

            boolean isSendSuccess = rePasswordEmailProvider.sendVerificationEmail(accountId, rePasswordNum);
            if (!isSendSuccess) return SendRepasswordResponseDto.mailSendFail();

            String encodedRePasswordNum = passwordEncoder.encode(rePasswordNum);

            user.updatePassword(encodedRePasswordNum);

        } catch (Exception e) {
            log.info("임시비밀번호 재설정 실패: {}", e);
            return SendRepasswordResponseDto.databaseError();
        }
        return SendRepasswordResponseDto.success();
    }

    @Transactional
    public ResponseEntity<? super RepasswordResponseDto> repassword(RepasswordInfo info) {
        try {
            Long userId = jwtExtractProvider.findIdFromJwt();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

            String currentPassword = info.getCurrentPassword();

            boolean isPasswordValid = passwordEncoder.matches(currentPassword, user.getPassword());
            if (!isPasswordValid) {
                return RepasswordResponseDto.wrongPassword();
            }
            String newPassword = info.getNewPassword();

            String encodedNewPassword = passwordEncoder.encode(newPassword);

            user.updatePassword(encodedNewPassword);

        } catch (Exception e) {
            log.info("비밀번호 재설정 실패: {}", e);
            return RepasswordResponseDto.databaseError();
        }
        return RepasswordResponseDto.success();
    }
}
