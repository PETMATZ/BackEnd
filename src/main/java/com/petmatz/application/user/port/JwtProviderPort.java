package com.petmatz.application.user.port;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface JwtProviderPort {

    String createAccessToken(Long userId, String accountId);

    String createRefreshToken(Long userId);

    String refreshAccessToken(String refreshToken);

    Map<String, Object> validate(String jwt);

    Long validateAndGetUserId(String token);

}
