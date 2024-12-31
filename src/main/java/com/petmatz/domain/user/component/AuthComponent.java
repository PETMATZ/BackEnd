package com.petmatz.domain.user.component;

import com.petmatz.common.security.utils.JwtProvider;
import com.petmatz.domain.aws.AwsClient;
import com.petmatz.domain.aws.vo.S3Imge;
import com.petmatz.domain.user.entity.Certification;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.entity.UserFactory;
import com.petmatz.domain.user.info.CheckCertificationInfo;
import com.petmatz.domain.user.info.SignInInfo;
import com.petmatz.domain.user.info.SignUpInfo;
import com.petmatz.domain.user.repository.CertificationRepository;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.CheckCertificationResponseDto;
import com.petmatz.domain.user.response.SignInResponseDto;
import com.petmatz.domain.user.response.SignUpResponseDto;
import com.petmatz.domain.user.service.GeocodingService;
import com.petmatz.user.common.LogInResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthComponent {

    /**
     * awsClient -> 추후에 infra 로 옮길 예정
     * 현재 1차로 분리작업만 하는중
     */

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final GeocodingService geocodingService;
    private final AwsClient awsClient; // 추후에 수정
    private final JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpInfo info) {
        try {
            String accountId = info.getAccountId();
            String certificationNumber = info.getCertificationNumber();

            // 1. 필수 정보 누락 확인
            if (accountId == null || certificationNumber == null || info.getPassword() == null) {
                return SignUpResponseDto.missingRequiredFields();
            }

            // 2. 인증 번호 확인
            Certification certification = certificationRepository.findTopByAccountIdOrderByCreatedAtDesc(accountId);
            if (certification == null || !certification.getIsVerified()) {
                return SignUpResponseDto.certificationFail(); // 인증되지 않은 경우
            }

            // 3. 중복된 ID 확인
            if (userRepository.existsByAccountId(accountId)) {
                return SignUpResponseDto.duplicateId();
            }

            // 5. 비밀번호 암호화 후 저장
            String encodedPassword = passwordEncoder.encode(info.getPassword());

            // 6. GeocodingService를 통해 지역명과 6자리 행정코드 가져오기
            GeocodingService.KakaoRegion kakaoRegion = geocodingService.getRegionFromCoordinates(info.getLatitude(), info.getLongitude());
            if (kakaoRegion == null || kakaoRegion.getCodeAsInteger() == null) {
                return SignUpResponseDto.locationFail();
            }

            //6-1 Img 정제
            S3Imge petImg = awsClient.UploadImg(info.getAccountId(), info.getProfileImg(), "CUSTOM_USER_IMG", null);

            // 7. 새로운 User 생성 및 저장
            User user = UserFactory.createNewUser(info, encodedPassword, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger(), petImg.uploadURL());
            userRepository.save(user);

            // 8. 인증 엔티티 삭제
            certificationRepository.deleteAllByAccountId(accountId);

            // 9. 성공 응답 반환
            return SignUpResponseDto.success(user.getId(), petImg.checkResultImg());

        } catch (RuntimeException e) {
            log.error("회원 가입 실패: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("회원 가입 중 처리되지 않은 예외 발생: {}", e.getMessage(), e);
            return SignUpResponseDto.unknownError();
        }
    }


    public ResponseEntity<? super SignInResponseDto> signIn(SignInInfo info, HttpServletResponse response) {
        try {
            String accountId = info.getAccountId();
            User user = userRepository.findByAccountId(accountId);
            // 사용자 존재 여부 확인
            if (user == null) {
                log.info("사용자 조회 실패: {}", accountId);
                return SignInResponseDto.signInFail();
            }

            // 비밀번호 확인
            String password = info.getPassword();
            String encodedPassword = user.getPassword();
            if (!passwordEncoder.matches(password, encodedPassword)) {
                log.info("비밀번호 불일치: {}", accountId);
                return SignInResponseDto.signInFail();
            }

            // JWT 생성 (userId를 subject로, accountId를 클레임으로 설정)
            String token = jwtProvider.create(user.getId(), user.getAccountId());
            log.info("JWT 생성 완료: {}", token);

            ResponseCookie responseCookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)           // XSS 방지
                    .secure(true)             // HTTPS만 허용
                    .path("/")                // 모든 경로에서 접근 가능
                    .sameSite("None")         // SameSite=None 설정
                    .maxAge((3600))
                    .build();
            response.addHeader("Set-Cookie", responseCookie.toString());
            // 로그인 성공 응답 반환
            return SignInResponseDto.success(user); // User 객체 전달
        } catch (Exception e) {
            log.error("로그인 처리 중 예외 발생", e);
            return SignInResponseDto.signInFail();
        }
    }

    public ResponseEntity<? super LogInResponseDto> logout(HttpServletResponse response) {
        try {
            // 만료된 쿠키 설정
            ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                    .httpOnly(true)           // XSS 방지
                    .secure(true)             // HTTPS만 허용
                    .path("/")                // 모든 경로에서 접근 가능
                    .sameSite("None")         // SameSite=None 설정
                    .maxAge(0)                // 즉시 만료
                    .build();

            response.addHeader("Set-Cookie", expiredCookie.toString());

            log.info("JWT 쿠키 제거 및 로그아웃 처리 완료");
            return LogInResponseDto.success();
        } catch (Exception e) {
            log.error("로그아웃 처리 중 예외 발생", e);
            return LogInResponseDto.validationFail();
        }
    }

    @Transactional
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationInfo info) {
        try {
            String accountId = info.getAccountId();
            String certificationNumber = info.getCertificationNumber();

            Certification certification = certificationRepository.findTopByAccountIdOrderByCreatedAtDesc(accountId);
            if (certification == null) return CheckCertificationResponseDto.certificationFail();

            boolean isMatch = certification.getAccountId().equals(accountId)
                    && certification.getCertificationNumber().equals(certificationNumber);

            if (!isMatch) return CheckCertificationResponseDto.certificationFail();

            // 인증 시간 검증
            LocalDateTime createdAt = certification.getCreatedAt();
            if (createdAt.isBefore(LocalDateTime.now().minusMinutes(5))) {  // 5분 유효시간
                return CheckCertificationResponseDto.certificationExpired();
            }

            // 인증 완료 상태 업데이트
            certification.markAsVerified();
            certificationRepository.save(certification);

        } catch (Exception e) {
            log.info("인증 번호 확인 실패: {}", e);
            return CheckCertificationResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();
    }
}
