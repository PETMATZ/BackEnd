package com.petmatz.application.user.validator;


import com.petmatz.infra.jwt.JwtManager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationValidator {

//    private final CertificationRepository certificationRepository;
//    private final UserRepository userRepository;
    private final JwtManager jwtManager;
//    private final UserUtils userUtils;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    public String createJwtAccessToken(User user) {
//        return jwtManager.createAccessToken(user.getId(), user.getAccountId());
//    }
//
//    public String createJwtRefreshToken(User user) {
//        return jwtManager.createRefreshToken(user.getId());
//    }

//    public User validateSignInCredentials(SignInInfo info) throws AuthenticationException {
//        String accountId = info.getAccountId();
//        String password = info.getPassword();
//
//        User user = userUtils.findUser(accountId);
//
//        String encodedPassword = user.getPassword();
//        if (!passwordEncoder.matches(password, encodedPassword)) {
//            throw new UserException(PASS_WORD_MISMATCH);
//        }
//        return user;
//    }

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
//    public void validateCertification(String accountId) {
//        Certification certification = certificationRepository.findTopByAccountIdOrderByCreatedAtDesc(accountId);
//        if (certification == null || !certification.getIsVerified()) {
//            throw new IllegalStateException("인증 번호가 유효하지 않거나 인증되지 않았습니다.");
//        }
//    }

    /**
     * 예외는 추후에 수정 (일단은 임시로 userEx 사용)
     */
//    public Certification validateCertification(CheckCertificationInfo info) throws CertificateException {
//        Certification certification = certificationRepository.findTopByAccountIdOrderByCreatedAtDesc(info.getAccountId());
//        if (certification == null) {
//            throw new CertificateException("인증 정보가 없습니다.");
//        }
//        // 인증 번호와 계정 ID 일치 여부 확인
//        boolean isMatch = certification.getAccountId().equals(info.getAccountId())
//                && certification.getCertificationNumber().equals(info.getCertificationNumber());
//
//        if (!isMatch) {
//            throw new UserException(MISS_MATCH_CODE);
//        }
//
//        // 인증 시간 확인
//        if (certification.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
//            throw new UserException(CERTIFICATION_EXPIRED);
//        }
//        return certification;
//    }
//
//    public void updateCertificationStatus(Certification certification) {
//        certification.markAsVerified();
//        certificationRepository.save(certification);
//    }
}
