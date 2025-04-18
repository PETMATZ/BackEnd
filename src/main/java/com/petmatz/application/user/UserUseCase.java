package com.petmatz.application.user;



import com.petmatz.api.user.dto.SignUpResponse;
import com.petmatz.application.email.EmailUseCase;
import com.petmatz.application.user.dto.SignUpInfo;
import com.petmatz.application.user.dto.RegionInfo;
import com.petmatz.application.user.dto.SignInInfo;
import com.petmatz.application.user.exception.UserException;
import com.petmatz.application.user.port.JwtProviderPort;
import com.petmatz.application.user.port.UserUseCasePort;
import com.petmatz.common.security.jwt.JwtExtractProvider;
import com.petmatz.domain.user.port.GeocodingPort;
import com.petmatz.domain.user.utils.UserFactory;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserCommandPort;
import com.petmatz.domain.user.port.UserQueryPort;
import com.petmatz.application.user.validator.AuthenticationValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.petmatz.application.user.exception.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUseCase implements UserUseCasePort {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final EmailUseCase emailUseCase;

    private final UserCommandPort userCommandPort;
    private final UserQueryPort userQueryPort;
    private final JwtProviderPort jwtProviderPort;
    private final GeocodingPort geocodingPort;
    private final JwtExtractProvider jwtExtractProvider;


    private AuthenticationValidator authenticationValidator;

    @Override
    public SignInInfo signIn(String accountId, String password) {
        if (!userQueryPort.existsByAccountId(accountId)) {
            throw new UserException(USER_NOT_FOUND);
        }
        User userInfo = userQueryPort.findByUserInfo(accountId);
        userInfo.checkAccountIdAndPassword(accountId, passwordEncoder.encode(password));
        String refreshToken = jwtProviderPort.createRefreshToken(userInfo.getId());
        String accessToken = jwtProviderPort.createAccessToken(userInfo.getId(), userInfo.getAccount().getAccountId());
        return new SignInInfo(userInfo, refreshToken, accessToken);
    }


    @Override
    @Transactional
    public SignUpResponse signUp(SignUpInfo info){
        String accountId = info.getAccountId();
        authenticationValidator.validateRequiredFields(accountId, info.getCertificationNumber(), info.getPassword());
        if (userQueryPort.existsByAccountId(accountId)) {
            throw new IllegalArgumentException("중복된 ID가 존재합니다.");
        }
        String encodedPassword = passwordEncoder.encode(info.getPassword());
        info.setPassword(encodedPassword);
        // 지역명과 6자리 행정코드 가져오기
        RegionInfo region = geocodingPort.getRegion(info.getLatitude(), info.getLongitude());

        //Img 정제
//        S3Imge petImg = awsClient.UploadImg(info.getAccountId(), info.getProfileImg(), "CUSTOM_USER_IMG", null);

        //TODO AWS 살리면 아래꺼 써야함.
        //새로운 User 생성 및 저장
//        User user = UserFactory.createNewUser(info, encodedPassword, region.getRegion(), region.code(), petImg.uploadURL());
        User user = UserFactory.createNewUser(info, region.getRegion(), region.code(), null);

        Long userId = userCommandPort.save(user);

        //인증 엔티티 삭제
        emailUseCase.deleteCertification(accountId);

        //TODO AWS 살리면 아래꺼 써야함.
//        return new SignUpResponse(user.getId(), petImg.checkResultImg());
        return new SignUpResponse(userId, null);
    }

    @Override
    @Transactional
    public void updateLocation(Double latitude, Double longitude) {
        String accountId = jwtExtractProvider.findAccountIdFromJwt();

        RegionInfo region = geocodingPort.getRegion(latitude, longitude);
        userCommandPort.updateUserLocation(latitude,longitude, region.getRegion(), region.code(), accountId);
    }

//    @Transactional
//    public void checkCertification(CheckCertificationInfo info) throws CertificateException {
//            Certification certification = authenticationComponent.validateCertification(info);
//            authenticationComponent.updateCertificationStatus(certification);
//    }
}
