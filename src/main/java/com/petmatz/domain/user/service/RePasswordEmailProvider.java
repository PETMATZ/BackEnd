package com.petmatz.domain.user.service;

public interface RePasswordEmailProvider {
    void sendVerificationEmail(String email, String rePassword);
}
