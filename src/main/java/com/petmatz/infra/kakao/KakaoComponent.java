package com.petmatz.infra.kakao;

import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserCommandPort;
import com.petmatz.domain.user.port.UserQueryPort;
import com.petmatz.persistence.user.mapper.KaKaoUserDomain;
import com.petmatz.infra.security.vo.CustomOAuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoComponent extends DefaultOAuth2UserService {

    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // RegistrationId 체크
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (!"kakao".equals(registrationId)) {
            throw new OAuth2AuthenticationException("Unsupported registrationId: " + registrationId);
        }

        //  id 추출
        String kakaoAccountId = attributes.get("id").toString(); // 고유 ID
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        // 이메일 추출
        String email = (String) kakaoAccount.get("email");
        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationException("Email is required for Kakao login.");
        }

        // 닉네임 추출
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.getOrDefault("nickname", "Unknown User");

        // 이미지 추출 (선택)
        String profileImage = (String) profile.getOrDefault("profile_image_url", "");


        User user = userQueryPort.findAccountIdByUserInfo(email);
        if (user == null) {
            user = KaKaoUserDomain.createUser(kakaoAccountId, email, nickname, profileImage);
            userCommandPort.save(user);
        }

        return new CustomOAuthUser(user.getId(), user.getAccount().getAccountId(), attributes, oAuth2User.getAuthorities());
    }
}
