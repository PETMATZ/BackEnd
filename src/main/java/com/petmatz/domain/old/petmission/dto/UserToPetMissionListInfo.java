//package com.petmatz.domain.old.petmission.dto;
//
//import com.petmatz.api.petmission.dto.PetMissionUser;
//import com.petmatz.common.constants.PetMissionStatusZip;
//import com.petmatz.domain.old.petmission.entity.PetMissionAskEntity;
//import com.petmatz.domain.old.petmission.entity.PetMissionEntity;
//import com.petmatz.domain.old.petmission.entity.PetToPetMissionEntity;
//import com.petmatz.domain.old.petmission.entity.UserToPetMissionEntity;
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Builder
//public record UserToPetMissionListInfo(
//
//        List<PetMissionUser> petMissionUsers,
//
//        Long missionId,
//
//        List<String> comment,
//
//        LocalDateTime petMissionStarted,
//
//        LocalDateTime petMissionEnd,
//
//        PetMissionStatusZip status,
//
//        List<PetInfo> petInfo
//
//) {
//
//
//    public static UserToPetMissionListInfo of(UserToPetMissionEntity userToPetMissionEntity, List<UserToPetMissionEntity> userToPetMissionEntityList) {
//        PetMissionEntity petMission = userToPetMissionEntity.getPetMission();
//        List<PetToPetMissionEntity> petToPetMissions = petMission.getPetToPetMissions();
//        return UserToPetMissionListInfo.builder()
//                .petMissionUsers(userToPetMissionEntityList.stream().map(PetMissionUser::of).toList())
//                .missionId(userToPetMissionEntity.getPetMission().getId())
//                .comment(petMission.getPetMissionAsks().stream().map(PetMissionAskEntity::getComment).toList())
//                .petMissionStarted(petMission.getPetMissionStarted())
//                .petMissionEnd(petMission.getPetMissionEnd())
//                .status(petMission.getStatus())
//                .petInfo(petToPetMissions.stream().map(pet -> PetInfo.of(pet.getPet())).toList())
//                .build();
//    }
//
//}
