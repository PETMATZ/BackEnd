package garbege.service.user.service;

import garbege.api.user.request1.DeleteIdRequestDto;
import com.petmatz.domain.pet.repository.PetRepository;
import com.petmatz.domain.old.sosboard.component.SosBoardDelete;
import garbege.service.user.component.PasswordComponent;
import garbege.service.user.provider.UserUtils;
import com.petmatz.garbege.service.user.User;
import garbege.service.user.info.UserInfo;
import com.petmatz.persistence.email.repository.CertificationRepository;
import com.petmatz.persistence.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserService {

    /**
     * 여기 종원님이랑 머지하고 펫레포 최신버전 받아오고 수정 예정 25.01.14
     */

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    private final SosBoardDelete sosBoardDelete;

    private final CertificationRepository certificationRepository;
    private final JwtExtractProvider jwtExtractProvider;
    private final UserUtils userUtils;
    private final PasswordComponent passwordComponent;


    @Transactional
    public void deleteId(DeleteIdRequestDto dto) {
        Long userId = jwtExtractProvider.findIdFromJwt();
        User user = userUtils.findIdUser(userId);

        String password = dto.getPassword();
        String encodedPassword = user.getPassword();

        //패스워드 검증
        passwordComponent.validatePassword(password, encodedPassword);

        //인증번호 관련 전부 삭제
        //TODO 인증 번호를 굳이 DB에서 관리해야 하나?
        certificationRepository.deleteById(userId);

        //sos보드 삭제
        sosBoardDelete.deleteSosBoardByUser(userId);

        //찜 목록 삭제

        //채팅방 삭제

        // 명시적으로 Pet 삭제
//        petRepository.deleteAll(pets);
        petRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }


    public UserInfo selectUserInfo(String receiverEmail) {
        User otherUser = userRepository.findByAccountId(receiverEmail);
        return otherUser.of();
    }

    public String findByUserEmail(Long userId) {
        return userRepository.findById(userId).get().getAccountId();
    }

    public void deleteUser(Long userUUID) {
        userRepository.deleteUserById(userUUID);
    }
}
