package com.petmatz.common.security.jwt;

import com.petmatz.domain.user.component.CookieComponent;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.infra.redis.component.RedisTokenComponent;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 토큰 생성 및 검증을 담당하는 클래스.
 * 주어진 사용자 ID로 JWT 토큰을 생성 -> 토큰의 유효성을 검증하여 사용자 ID를 반환
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class  JwtManager {

    @Value("${secret-access-key}")
    private String accessKey;

    @Value("${secret-refresh-key}")
    private String refreshKey;

    private final RedisTokenComponent redisTokenComponent;
    private final CookieComponent cookieComponent;
    private final UserUtils userUtils;

    /**
     * 주어진 사용자 ID와 계정 ID로 JWT 토큰을 생성하는 메서드.
     * 토큰은 1시간 동안 유효하며, 사용자 ID를 서브젝트로 설정하고 계정 ID를 클레임으로 추가.
     *
     * @return 생성된 JWT 토큰 문자열
     */
    public String createAccessToken(Long userId, String accountId) {
        // 토큰 만료 시간 설정 (1시간 후)
        Date expireDate = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));

        // 비밀 키 생성
        Key key = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));

        // JWT 토큰 생성
        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)  // 서명 알고리즘 및 키 설정
                .setSubject(userId.toString())            // 서브젝트에 사용자 ID 설정
                .claim("accountId", accountId)            // 계정 ID를 클레임으로 추가
                .setIssuedAt(new Date())                  // 토큰 발행 시간
                .setExpiration(expireDate)                // 토큰 만료 시간
                .compact();
        log.info("Generated JWT: {}", jwt);
        return jwt;
    }

    public String createRefreshToken(Long userId) {
        Date expireDate = Date.from(LocalDateTime.now()
                .plusWeeks(7) // 7주 추가
                .atZone(ZoneId.systemDefault())
                .toInstant());

        SecretKey secretKey = Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));

        String refreshToken = Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();

        redisTokenComponent.saveRefreshToken(userId, refreshToken);

        return refreshToken;
    }

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

    // refresh Token 으로 재발급
    public void refreshAccessToken(HttpServletResponse response, String refreshToken) {
        try {
            SecretKey refreshKey = Keys.hmacShaKeyFor(this.refreshKey.getBytes(StandardCharsets.UTF_8));

            // Refresh Token 검증 및 파싱
            var claims = Jwts.parserBuilder()
                    .setSigningKey(refreshKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            Long userId = Long.parseLong(claims.getSubject());
            String accountId = userUtils.findAccountIdByUserId(userId);

            String storedToken = redisTokenComponent.getRefreshTokenFromRedis(userId);
            if (storedToken == null || !storedToken.equals(refreshToken)) {
                log.error("Redis에 없는 리프레시 토큰이거나. 일치하지 않는 토큰입니당 다시 한번 확인해주세요");
            }

            String accessToken = createAccessToken(userId, accountId);
            cookieComponent.setAccessTokenCookie(response, accessToken);
        } catch (ExpiredJwtException e) {
            // Refresh Token 만료 시 예외 처리
            log.error("Refresh token has expired." + e);
        } catch (Exception e) {
            // 기타 검증 실패 시 예외 처리
            log.error("Invalid refresh token." + e);
        }
    }
}