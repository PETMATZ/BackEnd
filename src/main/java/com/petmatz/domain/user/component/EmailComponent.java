package com.petmatz.domain.user.component;

import com.petmatz.domain.user.entity.Certification;
import com.petmatz.domain.user.repository.CertificationRepository;
import com.petmatz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailComponent {
    
    private final CertificationRepository certificationRepository;

    public void saveCertification(String accountId, String certificationNumber) {
        Certification certification = Certification.builder()
                .accountId(accountId)
                .certificationNumber(certificationNumber)
                .isVerified(false)
                .build();
        certificationRepository.save(certification);
    }

}
