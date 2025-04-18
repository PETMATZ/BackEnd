package garbege.service.user.service;

public interface EmailProvider {
    void sendVerificationEmail(String email, String certificationNumber);

    void sendRePasswordEmail(String email, String rePassword);
}
