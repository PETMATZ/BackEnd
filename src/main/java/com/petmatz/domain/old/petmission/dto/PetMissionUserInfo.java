//package com.petmatz.domain.old.petmission.dto;
//
//import com.petmatz.garbege.service.user.User;
//import lombok.Builder;
//
//@Builder
//public record PetMissionUserInfo(
//
//        Long userId,
//        String userProfileURL
//
//) {
//
//    public static PetMissionUserInfo of(User user) {
//        return PetMissionUserInfo.builder()
//                .userId(user.getId())
//                .userProfileURL(user.getProfileImg())
//                .build();
//    }
//
//}
