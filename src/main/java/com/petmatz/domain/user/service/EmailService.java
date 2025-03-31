package com.petmatz.domain.user.service;

import com.petmatz.api.user.request.EmailCertificationRequestDto;
import com.petmatz.domain.user.component.EmailComponent;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.provider.CertificationNumberProvider;
import com.petmatz.domain.user.response.GetMyUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailProvider emailProvider;
    private final EmailComponent emailComponent;
    private final UserUtils userUtils;


    public void emailCertification(EmailCertificationRequestDto dto) {
        String accountId = dto.getAccountId();

        userUtils.checkDuplicateAccountId(accountId);

        // 인증 번호 생성 및 이메일 전송
        String certificationNumber = CertificationNumberProvider.generateNumber();
        emailProvider.sendVerificationEmail(accountId, certificationNumber);
        emailComponent.saveCertification(accountId, certificationNumber);
    }


    public GetMyUserDto receiverEmail(String accountId) {
        User user = userUtils.findUser(accountId);
        return new GetMyUserDto(user);
    }
}
