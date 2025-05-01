package com.petmatz.application.email.support;

import java.util.UUID;

public class CertificationNumberProvider {
    public static String generateNumber() {
        // UUID 생성 후 하이픈을 제거한 문자열로 변환
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, 6);
    }
}
