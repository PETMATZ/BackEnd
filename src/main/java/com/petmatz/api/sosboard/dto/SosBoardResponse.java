//package com.petmatz.api.sosboard.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.petmatz.domain.old.sosboard.PaymentType;
//import com.petmatz.domain.old.sosboard.dto.SosBoardInfo;
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Builder
//public record SosBoardResponse(
//
//        Long id, //게시글 id
//        Long userId, //유저 id
//        String accountId,
//        String title,
//        String comment,
//        PaymentType paymentType,
//        Integer price,
//        List<SosBoardPetResponse> petInfos, // PetResponse를 포함
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
//    public static SosBoardResponse of(SosBoardInfo sosBoard) {
//        return SosBoardResponse.builder()
//                .id(sosBoard.id())
//                .userId(sosBoard.userId())
//                .accountId(sosBoard.accountId())
//                .title(sosBoard.title())
//                .comment(sosBoard.comment())
//                .paymentType(sosBoard.paymentType())
//                .price(sosBoard.price())
//                .startDate(sosBoard.startDate())
//                .endDate(sosBoard.endDate())
//                .petInfos(sosBoard.petInfos().stream().map(SosBoardPetResponse::of).toList())
//                .authorNickname(sosBoard.authorNickname())
//                .authorProfileImg(sosBoard.authorProfileImg())
//                .authorGender(sosBoard.authorGender())
//                .authorRegion(sosBoard.authorRegion())
//                .createdAt(sosBoard.createdAt())
//                .updatedAt(sosBoard.updatedAt())
//                .build();
//
//    }
//
//}
//
