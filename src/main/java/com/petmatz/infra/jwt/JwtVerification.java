package com.petmatz.infra.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtVerification {

    @Value("${secret-access-key}")
    private String accessKey;

    @Value("${secret-refresh-key}")
    private String refreshKey;

    // 토큰 검증
    public Map<String, Object> validate(String jwt) {
        Key key = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));

        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            // 서브젝트에서 사용자 ID 추출
            Long userId = Long.parseLong(claims.getSubject());
            // 클레임에서 계정 ID 추출
            String accountId = claims.get("accountId", String.class);

            // 결과 맵 생성
            Map<String, Object> result = new HashMap<>();
            result.put("userId", userId);
            result.put("accountId", accountId);
            return result;

        } catch (ExpiredJwtException e) {
            log.warn("Access Token expired: {}", jwt);
            return null;
        } catch (Exception e) {
            log.error("Invalid Access Token: {}", jwt, e);
            return null;
        }
    }

    public Long validateAndGetUserId(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return Long.parseLong(subject); // subject를 Long 타입으로 변환
        } catch (ExpiredJwtException e) {
            log.warn("Access Token expired" + e);
            return null;
        } catch (Exception e) {
            log.error("Invalid Access Token: {}" + e);
            return null;
        }
    }


}
