package com.petmatz.infra.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${secret-access-key}")
    private String accessKey;

    @Value("${secret-refresh-key}")
    private String refreshKey;

    public String createAccessToken(Long userId, String accountId) {
        // 토큰 만료 시간 설정 (1시간 후)
        Date expireDate = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));

        // 비밀 키 생성
        Key key = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));

        // JWT 토큰 생성
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)  // 서명 알고리즘 및 키 설정
                .setSubject(userId.toString())            // 서브젝트에 사용자 ID 설정
                .claim("accountId", accountId)            // 계정 ID를 클레임으로 추가
                .setIssuedAt(new Date())                  // 토큰 발행 시간
                .setExpiration(expireDate)                // 토큰 만료 시간
                .compact();
    }

    public String createRefreshToken(Long userId) {
        Date expireDate = Date.from(Instant.now().plus(2, ChronoUnit.WEEKS));
        SecretKey secretKey = Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.RS256)
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    public boolean validate(String jwt, boolean isAccessToken) {
        try {
            Key key;
            if (isAccessToken) {
                key = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
            } else {
                key = Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));
            }
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
