package com.petmatz.application.user;


import com.petmatz.api.auth.dto.SignUpResponse;
import com.petmatz.application.email.EmailUseCase;
import com.petmatz.application.user.dto.SignUpInfo;
import com.petmatz.application.user.dto.RegionInfo;
import com.petmatz.application.user.dto.SignInInfo;
import com.petmatz.application.user.exception.UserException;
import com.petmatz.application.user.port.JwtProviderPort;
import com.petmatz.application.user.port.UserUseCasePort;
import com.petmatz.application.user.validator.PasswordValidator;
import com.petmatz.common.security.jwt.JwtExtractProvider;
import com.petmatz.domain.user.model.Profile;
import com.petmatz.domain.user.port.GeocodingPort;
import com.petmatz.domain.user.utils.UserFactory;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserCommandPort;
import com.petmatz.domain.user.port.UserQueryPort;
import com.petmatz.application.user.validator.AuthenticationValidator;
import garbege.service.user.info.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.petmatz.application.user.exception.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUseCase implements UserUseCasePort {

    private final EmailUseCase emailUseCase;

    private final UserCommandPort userCommandPort;
    private final UserQueryPort userQueryPort;
    private final JwtProviderPort jwtProviderPort;
    private final GeocodingPort geocodingPort;
    private final JwtExtractProvider jwtExtractProvider;
    private final AuthenticationValidator authenticationValidator;
    private final PasswordValidator passwordValidator;

    @Override
    public SignInInfo signIn(String accountId, String password) {
        if (!userQueryPort.existsByAccountId(accountId)) {
            throw new UserException(USER_NOT_FOUND);
        }
        User userInfo = userQueryPort.findByUserInfo(accountId);
        userInfo.checkAccountIdAndPassword(accountId, passwordValidator.encodePassword(password));
        String refreshToken = jwtProviderPort.createRefreshToken(userInfo.getId());
        String accessToken = jwtProviderPort.createAccessToken(userInfo.getId(), userInfo.getAccount().getAccountId());
        return new SignInInfo(userInfo, refreshToken, accessToken);
    }

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpInfo info) {
        String accountId = info.getAccountId();
        authenticationValidator.validateRequiredFields(accountId, info.getCertificationNumber(), info.getPassword());
        if (userQueryPort.existsByAccountId(accountId)) {
            throw new IllegalArgumentException("중복된 ID가 존재합니다.");
        }
        String encodedPassword = passwordValidator.encodePassword(info.getPassword());
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
        userCommandPort.updateUserLocation(latitude, longitude, region.getRegion(), region.code(), accountId);
    }

    @Override
    public void updatePassword(String currentPassword, String newPassword, Long userId) {
        userCommandPort.updatePassword(currentPassword, newPassword, userId);
    }

    @Override
    public void secession(Long userId, String password) {
        User user = userQueryPort.findById(userId);
        String encodedPassword = user.getAccount().getPassword();

        //패스워드 검증
        passwordValidator.validatePassword(password, encodedPassword);
        userCommandPort.deleteUser(userId);
        //인증번호 관련 전부 삭제
        //TODO 인증 번호를 굳이 DB에서 관리해야 하나?
//        certificationRepository.deleteById(userId);

        //sos보드 삭제
//        sosBoardDelete.deleteSosBoardByUser(userId);

        //찜 목록 삭제

        //채팅방 삭제

        // 명시적으로 Pet 삭제
//        petRepository.deleteAll(pets);
//        petRepository.deleteByUserId(userId);
    }

    @Override
    public UserInfo selectUserInfo(String receiverEmail) {
        User user = userQueryPort.findByUserInfo(receiverEmail);
        Profile profile = user.getProfile();
        return new UserInfo(user.getId(), profile.getNickname(), profile.getNickname(), profile.getProfileImg());
    }

    @Override
    public String selectUserAccountId(Long userId) {
        return userQueryPort.findAccountIdByUserId(userId);
    }

}
