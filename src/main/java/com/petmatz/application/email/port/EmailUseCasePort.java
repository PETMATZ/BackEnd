package com.petmatz.application.email.port;

public interface EmailUseCasePort {

    void deleteCertification(String accountId);

    void emailCertification(String accountId);

    void checkCertification(String accountId, String certificationNumber);

    void sendRepassword(String accountId);
}
