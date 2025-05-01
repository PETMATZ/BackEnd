package com.petmatz.infra.jwt;

import com.petmatz.application.jwt.port.JwtUserPort;
import com.petmatz.application.jwt.port.JwtProviderPort;
import com.petmatz.domain.user.port.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class JwtExtractProvider implements JwtUserPort {

    private final JwtProviderPort jwtProviderPort; // JWT를 검증하고 ID를 추출하는 클래스
    private final UserQueryPort userQueryPort;

    @Override
    public Long findIdFromJwt() {
        try {
            // SecurityContextHolder에서 Authentication 객체 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return null; // 인증되지 않은 경우
            }

            // Principal에서 ID 추출
            Object principal = authentication.getPrincipal();

            if (principal instanceof Long) {
                return (Long) principal; // Principal이 Long 타입인 경우 직접 반환
            } else if (principal instanceof String) {
                // Principal이 String인 경우 JWT에서 ID 추출
                return jwtProviderPort.validateAndGetUserId((String) principal);
            } else {
                throw new IllegalArgumentException("Invalid principal type: " + principal.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 예외 발생 시 null 반환
        }
    }


    @Override
    public String findAccountIdFromJwt() {
        try {
            // SecurityContextHolder에서 Authentication 객체 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return null; // 인증되지 않은 경우
            }

            // Principal에서 데이터 추출
            Object principal = authentication.getPrincipal();

            if (principal instanceof Long) {
                // Principal이 Long 타입인 경우 userId로 accountId 조회
                Long userId = (Long) principal;
                return userQueryPort.findAccountIdByUserId(userId); // Repository 메서드 사용
            } else if (principal instanceof String) {
                // Principal이 String 타입인 경우 JWT로 간주하고 accountId 추출
                Map<String, Object> claims = jwtProviderPort.validate((String) principal);

                if (claims != null && claims.containsKey("accountId")) {
                    return (String) claims.get("accountId");
                }
            } else {
                // 예상치 못한 타입 처리
                throw new IllegalArgumentException("Invalid principal type: " + principal.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 로그 출력
        }

        return null; // 예외 발생 또는 인증되지 않은 경우 null 반환
    }
}