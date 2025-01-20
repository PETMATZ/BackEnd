package com.petmatz.common.security.jwt;


public interface JwtExtractProvider {
    Long findIdFromJwt();
    String findAccountIdFromJwt(); // Email 로도 사용
}
