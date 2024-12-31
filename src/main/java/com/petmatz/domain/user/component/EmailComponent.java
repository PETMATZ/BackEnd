package com.petmatz.domain.user.component;

import com.petmatz.api.user.request.EmailCertificationRequestDto;
import com.petmatz.domain.user.entity.Certification;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.provider.CertificationNumberProvider;
import com.petmatz.domain.user.repository.CertificationRepository;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.EmailCertificationResponseDto;
import com.petmatz.domain.user.response.GetMyUserDto;
import com.petmatz.domain.user.service.EmailProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailComponent {

    private final UserRepository userRepository;
    private final EmailProvider emailProvider;
    private final CertificationRepository certificationRepository;

    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String accountId = dto.getAccountId();

            //이메일 전송과 동시에 아이디 중복검사
            boolean isExistId = userRepository.existsByAccountId(accountId);
            if (isExistId) return EmailCertificationResponseDto.duplicateId();

            // 인증 번호 생성 및 이메일 전송
            String certificationNumber = CertificationNumberProvider.generateNumber();
            boolean isSendSuccess = emailProvider.sendVerificationEmail(accountId, certificationNumber);
            if (!isSendSuccess) return EmailCertificationResponseDto.mailSendFail();

            // 인증 엔티티 저장
            Certification certification = Certification.builder().accountId(accountId).certificationNumber(certificationNumber).isVerified(false).build();
            certificationRepository.save(certification);

        } catch (Exception e) {
            log.info("이메일 인증 실패: {}", e);
            return EmailCertificationResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    public GetMyUserDto receiverEmail(String accountId) {
        try {
            User user = userRepository.findByAccountId(accountId);
            return new GetMyUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new GetMyUserDto();
    }
}
