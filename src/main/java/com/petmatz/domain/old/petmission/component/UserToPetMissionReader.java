package com.petmatz.domain.old.petmission.component;

import com.petmatz.api.petmission.dto.PetMissionUpdateRequest;
import com.petmatz.domain.old.petmission.entity.UserToPetMissionEntity;
import com.petmatz.domain.old.petmission.repository.UserToPetMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserToPetMissionReader {

    private final UserToPetMissionRepository userToPetMissionRepository;

    //TODO 예외 처리 필요
    public List<UserToPetMissionEntity> selectUserToPetMissionList(Long userId) {
        return userToPetMissionRepository.selectUserToPetMissionList(userId);
    }

    public List<UserToPetMissionEntity> selectUserToPetMissionList(Long userId, LocalDate petMissionStart) {
        return userToPetMissionRepository.selectUserToPetMissionList(userId, petMissionStart);
    }

    public List<UserToPetMissionEntity> selectUserToPetMissionList(PetMissionUpdateRequest petMissionUpdateRequest) {
        return userToPetMissionRepository.selectUserToPetMissionList(petMissionUpdateRequest.petMissionId());
    }

    public List<UserToPetMissionEntity> selectUserToPetMissionList(String petMissionId) {
        return  userToPetMissionRepository.selectUserToPetMissionList(petMissionId);
    }





}
