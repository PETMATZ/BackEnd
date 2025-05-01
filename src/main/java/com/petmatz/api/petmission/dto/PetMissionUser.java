//package com.petmatz.api.petmission.dto;
//
//import com.petmatz.domain.old.petmission.entity.UserToPetMissionEntity;
//import lombok.Builder;
//
//@Builder
//public record PetMissionUser(
//
//        Long userId,
//        String userNickname,
//        String userProfileURL,
//
//        String roleType
//) {
//
//    public static PetMissionUser of(UserToPetMissionEntity userToPetMissionEntity) {
//        User user = userToPetMissionEntity.getUser();
//        return PetMissionUser.builder()
//                .userId(user.getId())
//                .userNickname(user.getNickname())
//                .userProfileURL(user.getProfileImg())
//                .roleType(userToPetMissionEntity.getRoleType().toString())
//                .build();
//    }
//}
