package com.petmatz.domain.old.petmission.entity;

import com.petmatz.domain.old.petmission.dto.RoleType;
import com.petmatz.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class UserToPetMissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_mission_id", nullable = false)
    private PetMissionEntity petMission;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder
    public UserToPetMissionEntity(User user, PetMissionEntity petMission, RoleType roleType) {
        this.user = user;
        this.petMission = petMission;
        this.roleType = roleType;
    }


    public static UserToPetMissionEntity of(User user, PetMissionEntity petMission, Long careId) {
        return UserToPetMissionEntity.builder()
                .user(user)
                .petMission(petMission)
                .roleType(checkRoleType(user.getId(), careId))
                .build();
    }

    public static RoleType checkRoleType(Long userId, Long careId) {
        return userId.equals(careId) ? RoleType.DOL : RoleType.MAL;
    }

//    public static boolean isListEmpty() {
//
//    }
}
