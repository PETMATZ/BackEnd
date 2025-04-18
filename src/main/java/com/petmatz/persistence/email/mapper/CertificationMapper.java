package com.petmatz.persistence.email.mapper;

import com.petmatz.persistence.email.CertificationEntity;

public class CertificationMapper {

    public static CertificationEntity toEntity(String accountId, String certificationNumber) {
        return CertificationEntity.builder()
                .accountId(accountId)
                .certificationNumber(certificationNumber)
                .isVerified(false)
                .build();
    }

}
