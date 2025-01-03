package com.petmatz.domain.petmission.utils;

import com.petmatz.domain.petmission.entity.PetMissionEntity;
import com.petmatz.domain.petmission.entity.UserToPetMissionEntity;
import com.petmatz.domain.user.entity.User;

import static com.petmatz.domain.petmission.entity.UserToPetMissionEntity.checkRoleType;

public class UserToPetMissionMapper {

    public static UserToPetMissionEntity of(User user, PetMissionEntity petMission, Long careId) {
        return UserToPetMissionEntity.builder()
                .user(user)
                .petMission(petMission)
                .roleType(checkRoleType(user.getId(), careId))
                .build();
    }

}
