package com.petmatz.infra.security.handler;

import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserQueryPort;
import com.petmatz.infra.jwt.JwtManager;
import garbege.service.user.entity.CustomOAuthUser;
import com.petmatz.persistence.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtManager jwtManager;
    private final UserQueryPort userQueryPort;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuthUser oAuth2User = (CustomOAuthUser) authentication.getPrincipal();

        // 사용자 정보
        Long userId = oAuth2User.getUserId();
        String accountId = oAuth2User.getName();

        // JWT 생성
        String token = jwtManager.createAccessToken(userId, accountId);

        // JWT 쿠키 설정
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true) // HTTPS 환경에서만 사용
                .path("/")
                .maxAge(3600) // 1시간 유효
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", jwtCookie.toString());
        User user = userQueryPort.findAccountIdByUserInfo(accountId);
        String redirectUrl =(user.getProfile().getGender()==null)
                ? "https://petmatz-fe.vercel.app/kakao-signup"
                : "https://petmatz-fe.vercel.app/kakao-login";

        // 클라이언트로 리다이렉트
        response.sendRedirect(redirectUrl);
    }
}