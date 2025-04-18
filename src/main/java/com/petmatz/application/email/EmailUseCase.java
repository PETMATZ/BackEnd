package com.petmatz.application.email;

import com.petmatz.application.email.port.EmailUseCasePort;

import com.petmatz.application.user.exception.UserException;
import com.petmatz.domain.email.port.CertificationCommandPort;
import com.petmatz.domain.email.port.CertificationQueryPort;
import com.petmatz.domain.email.port.EmailSenderPort;
import com.petmatz.domain.user.port.UserQueryPort;
import com.petmatz.application.hepler.CertificationNumberProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.petmatz.application.user.exception.UserErrorCode.USER_DUPLICATE;

@Service
@RequiredArgsConstructor
public class EmailUseCase implements EmailUseCasePort {

    private final CertificationCommandPort certificationCommandPort;
    private final CertificationQueryPort certificationQueryPort;
    private final UserQueryPort userQueryPort;
    private final EmailSenderPort emailSenderPort;

    @Override
    public void deleteCertification(String accountId) {
        certificationCommandPort.delete(accountId);
    }

    @Override
    public void emailCertification(String accountId) {
        if (userQueryPort.existsByAccountId(accountId)) {
            throw new UserException(USER_DUPLICATE);
        }

        // 인증 번호 생성 및 이메일 전송
        String certificationNumber = CertificationNumberProvider.generateNumber();
        emailSenderPort.sendVerificationEmail(accountId, certificationNumber);
        certificationCommandPort.save(accountId, certificationNumber);
    }

//    public Certification findCertification(String accountId) {
//        certificationQueryPort.findCertification(accountId);
//    }

    @Override
    public void checkCertification(String accountId, String certificationNumber){
            certificationQueryPort.findCertification(accountId);
    }


}
