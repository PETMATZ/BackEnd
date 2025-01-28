package com.petmatz.infra.redis.component;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisTokenComponent {

    private final RedisTemplate<String, Object> redisTemplate;
    public void saveRefreshToken(Long userId, String refreshToken) {
        String redisKey = "refreshToken:" + userId;
        try {
            redisTemplate.opsForValue().set(redisKey, refreshToken, Duration.ofDays(30));
        } catch (Exception e) {
            throw new IllegalStateException("일단은 리프레시 토큰 저장 오류입니다!");
        }
    }
    public String getRefreshTokenFromRedis(Long userId) {
        String redisKey = "refreshToken:" + userId;
        return (String) redisTemplate.opsForValue().get(redisKey);
    }
}
