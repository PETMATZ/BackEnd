//package garbege.service.user.component;
//
//import com.petmatz.garbege.service.user.Certification;
//import com.petmatz.persistence.email.repository.CertificationRepository;
//import com.petmatz.persistence.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class EmailComponent {
//
//    private final UserRepository userRepository;
//    private final CertificationRepository certificationRepository;
//
//    public void saveCertification(String accountId, String certificationNumber) {
//        Certification certification = Certification.builder()
//                .accountId(accountId)
//                .certificationNumber(certificationNumber)
//                .isVerified(false)
//                .build();
//        certificationRepository.save(certification);
//    }
//
//}
