package com.petmatz.infra.adapter.token;

import com.petmatz.application.user.port.JwtProviderPort;
import com.petmatz.infra.jwt.JwtManager;
import com.petmatz.infra.jwt.JwtVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAdapter implements JwtProviderPort {

    private final JwtManager jwtManager;
    private final JwtVerification jwtVerification;

    @Override
    public String createAccessToken(Long userId, String accountId) {
        return jwtManager.createAccessToken(userId, accountId);
    }

    @Override
    public String createRefreshToken(Long userId) {
        return jwtManager.createRefreshToken(userId);
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        return jwtManager.refreshAccessToken(refreshToken);
    }

    @Override
    public Map<String, Object> validate(String jwt) {
        return jwtVerification.validate(jwt);
    }

    @Override
    public Long validateAndGetUserId(String token) {
        return jwtVerification.validateAndGetUserId(token);
    }
}
