//package com.petmatz.domain.old.sosboard.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.petmatz.domain.old.sosboard.PaymentType;
//import com.petmatz.domain.old.sosboard.entity.SosBoard;
//import com.petmatz.garbege.service.user.User;
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Builder
//public record SpecificSosBoardInfo(
//        Long id, //게시글 id
//        Long userId, //유저 id
//        String accountId,
//        String title,
//        String comment,
//        PaymentType paymentType,
//        Integer price,
//        List<SosBoardPetInfo> petInfos, // PetResponse를 포함
//        String startDate, // 변환된 LocalDateTime
//        String endDate,    // 변환된 LocalDateTime
//        String authorNickname,
//        String authorProfileImg,
//        String authorGender,
//        String authorRegion,
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//        LocalDateTime createdAt, // 생성일시
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//        LocalDateTime updatedAt
//) {
//
//    public static SpecificSosBoardInfo of(SosBoard sosBoard) {
//        User user = sosBoard.getUser();
//        return SpecificSosBoardInfo.builder()
//                .id(sosBoard.getId())
//                .userId(user.getId())
//                .accountId(user.getAccountId())
//                .title(sosBoard.getTitle())
//                .comment(sosBoard.getComment())
//                .paymentType(sosBoard.getPaymentType())
//                .price(sosBoard.getPrice())
//                .startDate(sosBoard.getStartDate())
//                .endDate(sosBoard.getEndDate())
//                .petInfos(sosBoard.getPetSosBoards().stream().map(petSosBoard -> SosBoardPetInfo.of(petSosBoard.getPet())).toList())
//                .authorNickname(user.getNickname())
//                .authorProfileImg(user.getProfileImg())
//                .authorGender(user.getGender().toString())
//                .authorRegion(user.getRegion())
//                .createdAt(sosBoard.getCreatedAt())
//                .updatedAt(sosBoard.getUpdatedAt())
//                .build();
//
//    }
//}