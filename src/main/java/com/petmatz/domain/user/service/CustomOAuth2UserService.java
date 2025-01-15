package com.petmatz.domain.user.service;

import com.petmatz.domain.user.component.OAuth2UserLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService {

    private final List<OAuth2UserLoader> loaders;

    public OAuth2User loadUser(OAuth2UserRequest request) {
        String registrationId = request.getClientRegistration().getRegistrationId();

        return loaders.stream()
                .filter(loader -> loader.supports(registrationId))
                .findFirst()
                .orElseThrow(() -> new OAuth2AuthenticationException("지원되지 않는 id입니다 : " + registrationId))
                .loadUser(request);
    }
}
