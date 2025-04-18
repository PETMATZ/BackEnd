package com.petmatz.persistence.email.adapter;

import com.petmatz.domain.email.Certification;
import com.petmatz.domain.email.port.CertificationCommandPort;
import com.petmatz.domain.email.port.CertificationQueryPort;
import com.petmatz.persistence.email.CertificationEntity;
import com.petmatz.persistence.email.mapper.CertificationMapper;
import com.petmatz.persistence.email.repository.CertificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class EmailAdapter implements CertificationCommandPort, CertificationQueryPort {

    private final CertificationRepository certificationRepository;

    @Override
    public void delete(String accountId) {
        certificationRepository.deleteAllByAccountId(accountId);
    }

    @Override
    public void save(String accountId, String certificationNumber) {
        certificationRepository.save(CertificationMapper.toEntity(accountId, certificationNumber));
    }

    //TODO 예외 처리로 바꾸기
    @Override
    public void findCertification(String accountId) {
        certificationRepository.findCertificationNumber(accountId).orElseThrow(
                () -> new IllegalStateException("인증 번호가 유효하지 않거나 인증되지 않았습니다."));
    }


}
