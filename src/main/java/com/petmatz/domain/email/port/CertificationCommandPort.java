package com.petmatz.domain.email.port;


public interface CertificationCommandPort {

    void delete(String accountId);
    void save(String accountId, String certificationNumber);
}
