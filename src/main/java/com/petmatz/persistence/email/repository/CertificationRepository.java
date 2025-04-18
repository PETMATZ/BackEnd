package com.petmatz.persistence.email.repository;

import com.petmatz.persistence.email.CertificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<CertificationEntity, Long> {

    @Query("SELECT c.certificationNumber FROM Certification c WHERE c.accountId = :accountId")
    Optional<String> findCertificationNumber(String accountId);

    void deleteAllByAccountId(String accountId);
}
