package com.petmatz.domain.user.service;

import com.petmatz.common.security.utils.JwtProvider;
import com.petmatz.domain.aws.AwsClient;
import com.petmatz.domain.aws.vo.S3Imge;
import com.petmatz.domain.user.component.AuthenticationComponent;
import com.petmatz.domain.user.entity.Certification;
import com.petmatz.domain.user.entity.KakaoRegion;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.entity.UserFactory;
import com.petmatz.domain.user.info.CheckCertificationInfo;
import com.petmatz.domain.user.info.SignInInfo;
import com.petmatz.domain.user.info.SignUpInfo;
import com.petmatz.domain.user.repository.CertificationRepository;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.SignInResponseDto;
import com.petmatz.domain.user.response.SignUpResponseDto;
import com.petmatz.domain.user.component.GeocodingComponent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.net.MalformedURLException;
import java.security.cert.CertificateException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    /**
     * awsClient -> 추후에 infra 로 옮길 예정
     * 현재 1차로 분리작업만 하는중
     */

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final GeocodingComponent geocodingComponent;
    private final AwsClient awsClient; // 추후에 수정
    private final JwtProvider jwtProvider;
    private final AuthenticationComponent authenticationComponent;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public SignUpResponseDto signUp(SignUpInfo info) throws MalformedURLException {
        String accountId = info.getAccountId();
        String certificationNumber = info.getCertificationNumber();
        String password = info.getPassword();

        authenticationComponent.validateRequiredFields(accountId, certificationNumber, password);
        authenticationComponent.validateCertification(accountId);
        authenticationComponent.validateDuplicateAccountId(accountId);

        String encodedPassword = passwordEncoder.encode(info.getPassword());

        // 지역명과 6자리 행정코드 가져오기
        KakaoRegion kakaoRegion = geocodingComponent.getValidRegion(info.getLatitude(), info.getLongitude());

        //Img 정제
        S3Imge petImg = awsClient.UploadImg(info.getAccountId(), info.getProfileImg(), "CUSTOM_USER_IMG", null);

        //새로운 User 생성 및 저장
        User user = UserFactory.createNewUser(info, encodedPassword, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger(), petImg.uploadURL());
        userRepository.save(user);

        //인증 엔티티 삭제
        certificationRepository.deleteAllByAccountId(accountId);

        return new SignUpResponseDto(user.getId(), petImg.checkResultImg());
    }


    public SignInResponseDto signIn(SignInInfo info, HttpServletResponse response) throws AuthenticationException {
        User user = authenticationComponent.validateSignInCredentials(info);
        String token = authenticationComponent.createJwtToken(user);

        ResponseCookie responseCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)           // XSS 방지
                .secure(true)             // HTTPS만 허용
                .path("/")                // 모든 경로에서 접근 가능
                .sameSite("None")         // SameSite=None 설정
                .maxAge((3600))
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
        // 로그인 성공 응답 반환
        return new SignInResponseDto(user);
    }


    public void logout(HttpServletResponse response) { // 예외 처리
            // 만료된 쿠키 설정
            ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                    .httpOnly(true)           // XSS 방지
                    .secure(true)             // HTTPS만 허용
                    .path("/")                // 모든 경로에서 접근 가능
                    .sameSite("None")         // SameSite=None 설정
                    .maxAge(0)                // 즉시 만료
                    .build();
            response.addHeader("Set-Cookie", expiredCookie.toString());
    }

    @Transactional
    public void checkCertification(CheckCertificationInfo info) throws CertificateException {
            Certification certification = authenticationComponent.validateCertification(info);
            authenticationComponent.updateCertificationStatus(certification);
    }
}
