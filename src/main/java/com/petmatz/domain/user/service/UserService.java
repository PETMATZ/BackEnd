package com.petmatz.domain.user.service;

import com.petmatz.api.user.request.DeleteIdRequestDto;
import com.petmatz.common.security.jwt.JwtExtractProvider;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.pet.repository.PetRepository;
import com.petmatz.domain.user.component.PasswordComponent;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.UserInfo;
import com.petmatz.domain.user.repository.CertificationRepository;
import com.petmatz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserService {

    /**
     * 여기 종원님이랑 머지하고 펫레포 최신버전 받아오고 수정 예정 25.01.14
     */

    private final UserRepository userRepository;
    private final PetRepository petRepository;


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

        passwordComponent.validatePassword(password, encodedPassword);
        certificationRepository.deleteById(userId);
        // 사용자 삭제
        List<Pet> pets = petRepository.findAllByUserId(user.getId()); // Pet 엔티티에서 User를 참조하는 기준으로 조회
        // 명시적으로 Pet 삭제
        petRepository.deleteAll(pets);
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
