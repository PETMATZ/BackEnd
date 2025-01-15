package com.petmatz.domain.user.component;

import com.petmatz.common.security.utils.JwtProvider;
import com.petmatz.domain.user.entity.Certification;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.exception.UserException;
import com.petmatz.domain.user.info.CheckCertificationInfo;
import com.petmatz.domain.user.info.SignInInfo;
import com.petmatz.domain.user.repository.CertificationRepository;
import com.petmatz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;

import static com.petmatz.domain.user.exception.MatchErrorCode.CERTIFICATION_EXPIRED;
import static com.petmatz.domain.user.exception.MatchErrorCode.MISS_MATCH_CODE;

@Component
@RequiredArgsConstructor
public class AuthenticationComponent {

    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserUtils userUtils;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User validateSignInCredentials(SignInInfo info) throws AuthenticationException {
        String accountId = info.getAccountId();
        String password = info.getPassword();

        User user = userUtils.findUser(accountId);

        String encodedPassword = user.getPassword();
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new AuthenticationException("비밀번호 불일치");
        }
        return user;
    }

    public String createJwtToken(User user) {
        return jwtProvider.create(user.getId(), user.getAccountId());
    }

    /**
     * 필수 정보 누락 확인
     */
    public void validateRequiredFields(String accountId, String certificationNumber, String password) {
        if (accountId == null || certificationNumber == null || password == null) {
            throw new IllegalArgumentException("필수 정보가 누락되었습니다.");
        }
    }

    /**
     * 인증 번호 확인
     */
    public void validateCertification(String accountId) {
        Certification certification = certificationRepository.findTopByAccountIdOrderByCreatedAtDesc(accountId);
        if (certification == null || !certification.getIsVerified()) {
            throw new IllegalStateException("인증 번호가 유효하지 않거나 인증되지 않았습니다.");
        }
    }

    /**
     * 중복된 ID 확인
     */
    public void validateDuplicateAccountId(String accountId) {
        if (userRepository.existsByAccountId(accountId)) {
            throw new IllegalArgumentException("중복된 ID가 존재합니다.");
        }
    }

    /**
     * 예외는 추후에 수정 (일단은 임시로 userEx 사용)
     */
    public Certification validateCertification(CheckCertificationInfo info) throws CertificateException {
        Certification certification = certificationRepository.findTopByAccountIdOrderByCreatedAtDesc(info.getAccountId());
        if (certification == null) {
            throw new CertificateException("인증 정보가 없습니다.");
        }
        // 인증 번호와 계정 ID 일치 여부 확인
        boolean isMatch = certification.getAccountId().equals(info.getAccountId())
                && certification.getCertificationNumber().equals(info.getCertificationNumber());

        if (!isMatch) {
            throw new UserException(MISS_MATCH_CODE);
        }

        // 인증 시간 확인
        if (certification.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new UserException(CERTIFICATION_EXPIRED);
        }
        return certification;
    }

    public void updateCertificationStatus(Certification certification) {
        certification.markAsVerified();
        certificationRepository.save(certification);
    }
}
