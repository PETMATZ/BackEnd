package com.petmatz.domain.user.service;

import com.petmatz.common.security.jwt.JwtExtractProvider;
import com.petmatz.domain.user.component.GeocodingComponent;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import com.petmatz.domain.user.entity.KakaoRegion;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.exception.UserException;
import com.petmatz.domain.user.info.EditKakaoProfileInfo;
import com.petmatz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.petmatz.domain.user.exception.UserErrorCode.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoUserService {

    /**
     * 카카오 프로필 수정 어디다가 넣을지 고민중,,,
     */

    private final JwtExtractProvider jwtExtractProvider;
    private final UserRepository userRepository;
    private final GeocodingComponent geocodingComponent;
    private final UserUtils userUtils;

    @Transactional
    public void editKakaoProfile(EditKakaoProfileInfo info) {
        Long userId = jwtExtractProvider.findIdFromJwt();
        userUtils.findJwtUser(userId);
        userUtils.checkDuplicateId(userId);
        User user = userUtils.findIdUser(userId);

        KakaoRegion kakaoRegion = geocodingComponent.getRegionFromCoordinates(info.getLatitude(), info.getLongitude());
        if (kakaoRegion == null || kakaoRegion.getCodeAsInteger() == null) {
            throw new UserException(MISS_KAKAO_LOACTION);
        }

        user.updateKakaoProfile(info, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());
    }

    public User createNewKakaoUser(String kakaoAccountId, String email, String nickname, String profileImage) {
        User newUser = User.builder()
                .accountId(email) // 이메일을 accountId에 저장
                .registrationId(kakaoAccountId)
                .password("password") // 기본 비밀번호 설정 (필요시 변경)
                .nickname(nickname)
                .profileImg(profileImage) // 프로필 이미지 저장
                .loginRole(LoginRole.ROLE_USER) // 기본 역할 설정
                .loginType(LoginType.KAKAO) // 로그인 타입
                .careCompletionCount(0)
                .recommendationCount(0)
                .build();

        return userRepository.save(newUser);
    }
}
