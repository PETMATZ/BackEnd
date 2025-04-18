package com.petmatz.domain.email.port;

public interface EmailSenderPort {

    void sendVerificationEmail(String email, String rePassword);

    void sendRePasswordEmail(String email, String rePassword);

}
