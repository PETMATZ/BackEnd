package com.petmatz.persistence.email;

import com.petmatz.persistence.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity(name="Certification" )
@Table(name = "Certification"  )
/**
 * 이메일 검증을 위한 Entity
 */
public class CertificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_id", nullable = false, length = 30)
    private String accountId;

    @Column(name = "certification_number", nullable = false, length = 6)
    private String certificationNumber;

    @Column(name = "is_verified")
    private Boolean isVerified = false; // 인증 완료 상태

    public void markAsVerified() {
        this.isVerified = true;
    }
}
