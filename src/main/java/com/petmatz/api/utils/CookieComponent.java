package com.petmatz.api.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;


@Component
public class CookieComponent {

    public void setAccessTokenCookie(HttpServletResponse response, String accessToken) {
        ResponseCookie responseCookie = org.springframework.http.ResponseCookie.from("jwt", accessToken)
                .httpOnly(true)        // XSS 방지
                .secure(true)          // HTTPS만 허용
                .path("/")             // 모든 경로에서 접근 가능
                .sameSite("None")      // SameSite=None 설정
                .maxAge(3600)          // 1시간 유효
                .build();
        setCookieHeader(response, responseCookie);
    }

    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie responseCookie = org.springframework.http.ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)        // XSS 방지
                .secure(true)          // HTTPS만 허용
                .path("/")             // 모든 경로에서 접근 가능
                .sameSite("None")      // SameSite=None 설정
                .maxAge(60 * 60 * 24 * 14)  // 2주 동안 유효
                .build();
        setCookieHeader(response, responseCookie);
    }

    private static void setCookieHeader(HttpServletResponse response, ResponseCookie responseCookie) {
        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    public void logOut(HttpServletResponse response) {
                    ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                    .httpOnly(true)           // XSS 방지
                    .secure(true)             // HTTPS만 허용
                    .path("/")                // 모든 경로에서 접근 가능
                    .sameSite("None")         // SameSite=None 설정
                    .maxAge(0)                // 즉시 만료
                    .build();
            response.addHeader("Set-Cookie", expiredCookie.toString());
    }



}
