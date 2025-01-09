package com.petmatz.domain.user.service;

public interface EmailProvider {
    void sendVerificationEmail(String email, String certificationNumber);
}
