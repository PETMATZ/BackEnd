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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final UserRepository userRepository;
    private final EmailProvider emailProvider;
    private final EmailComponent emailComponent;


    public void emailCertification(EmailCertificationRequestDto dto) {
        String accountId = dto.getAccountId();

        emailComponent.checkDuplicateAccountId(accountId);

        // 인증 번호 생성 및 이메일 전송
        String certificationNumber = CertificationNumberProvider.generateNumber();
        emailProvider.sendVerificationEmail(accountId, certificationNumber);

        emailComponent.saveCertification(accountId, certificationNumber);
    }


    public GetMyUserDto receiverEmail(String accountId) {
        User user = userRepository.findByAccountId(accountId);
        return new GetMyUserDto(user);
    }
}
