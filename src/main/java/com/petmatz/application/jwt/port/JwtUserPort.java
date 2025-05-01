package com.petmatz.application.jwt.port;

public interface JwtUserPort {
    Long findIdFromJwt();           // 사용자 ID
    String findAccountIdFromJwt();
}
