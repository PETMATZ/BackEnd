//package com.petmatz.garbege.service.user.service;
//
//
//import com.petmatz.api.user.dto.SignUpResponse;
//import com.petmatz.domain.aws.AwsClient;
//import com.petmatz.domain.aws.vo.S3Imge;
//import com.petmatz.application.user.validator.AuthenticationValidator;
//import com.petmatz.api.global.utils.CookieComponent;
//import com.petmatz.domain.user.User;
//import com.petmatz.infra.adapter.kakao.dto.KakaoRegion;
//import com.petmatz.domain.user.utils.UserFactory;
//import com.petmatz.application.user.dto.SignInInfo;
//import com.petmatz.application.user.dto.SignUpInfo;
//import com.petmatz.persistence.email.repository.CertificationRepository;
//import com.petmatz.persistence.user.repository.UserRepository;
//import com.petmatz.application.user.dto.SignInResponseDto;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseCookie;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.naming.AuthenticationException;
//import java.net.MalformedURLException;
//import java.security.cert.CertificateException;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class AuthService {
//
//    /**
//     * awsClient -> 추후에 infra 로 옮길 예정
//     * 현재 1차로 분리작업만 하는중
//     */
//
//    private final UserRepository userRepository;
//    private final CertificationRepository certificationRepository;
////    private final GeocodingComponent geocodingComponent;
//    private final AwsClient awsClient; // 추후에 수정
//    private final CookieComponent cookieComponent;
//    private final AuthenticationValidator authenticationValidator;
//
//    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//
//    public SignInResponseDto signIn(SignInInfo info, HttpServletResponse response) throws AuthenticationException {
//        User user = authenticationValidator.validateSignInCredentials(info);
//        String accessToken = authenticationValidator.createJwtAccessToken(user);
//        String refreshToken = authenticationValidator.createJwtRefreshToken(user);
//
//        cookieComponent.setAccessTokenCookie(response, accessToken);
//        cookieComponent.setRefreshTokenCookie(response, refreshToken);
//
//        // 로그인 성공 응답 반환
//        return new SignInResponseDto(user);
//    }
//
//
//    //-----------------//-----------------//-----------------//-----------------//-----------------//-----------------//-----------------
//
//
//
//    @Transactional
//    public SignUpResponse signUp(SignUpInfo info) throws MalformedURLException {
//        String accountId = info.getAccountId();
//        String certificationNumber = info.getCertificationNumber();
//        String password = info.getPassword();
//
//        authenticationValidator.validateRequiredFields(accountId, certificationNumber, password);
//        authenticationValidator.validateDuplicateAccountId(accountId);
//
//        String encodedPassword = passwordEncoder.encode(info.getPassword());
//
//        // 지역명과 6자리 행정코드 가져오기
//        KakaoRegion kakaoRegion = geocodingComponent.getValidRegion(info.getLatitude(), info.getLongitude());
//
//        //Img 정제
//        S3Imge petImg = awsClient.UploadImg(info.getAccountId(), info.getProfileImg(), "CUSTOM_USER_IMG", null);
//
//        //새로운 User 생성 및 저장
//        User user = UserFactory.createNewUser(info, encodedPassword, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger(), petImg.uploadURL());
//        userRepository.save(user);
//
//        //인증 엔티티 삭제
//        certificationRepository.deleteAllByAccountId(accountId);
//
//        return new SignUpResponse(user.getId(), petImg.checkResultImg());
//    }
//
//
//    public void logout(HttpServletResponse response) { // 예외 처리
//            // 만료된 쿠키 설정
//            ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
//                    .httpOnly(true)           // XSS 방지
//                    .secure(true)             // HTTPS만 허용
//                    .path("/")                // 모든 경로에서 접근 가능
//                    .sameSite("None")         // SameSite=None 설정
//                    .maxAge(0)                // 즉시 만료
//                    .build();
//            response.addHeader("Set-Cookie", expiredCookie.toString());
//    }
//
//    @Transactional
//    public void checkCertification(CheckCertificationInfo info) throws CertificateException {
//            Certification certification = authenticationValidator.validateCertification(info);
//            authenticationValidator.updateCertificationStatus(certification);
//    }
//}
