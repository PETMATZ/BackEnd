//package com.petmatz.domain.old.sosboard.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.petmatz.domain.old.sosboard.PaymentType;
//import com.petmatz.domain.old.sosboard.entity.SosBoard;
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public record SosBoardInfoList(
//
//        List<SosBoardList> content,
//        Long totalCount,
//        Long totalPages,
//        Long currentPage
//
//
//
//) {
//
//    @Builder
//    public record SosBoardList(
//            Long id, //게시글 id
//            Long userId, //유저 id
//            String accountId,
//            String title,
//            String comment,
//            PaymentType paymentType,
//            Integer price,
//            List<Long> petIds,
//            List<SosBoardPetInfo> petInfos, // PetResponse를 포함
//            String startDate, // 변환된 LocalDateTime
//            String endDate,    // 변환된 LocalDateTime
//            String userNickname,
//            String userProfileImg,
//            String userGender,
//            String userRegion,
//            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//            LocalDateTime createdAt, // 생성일시
//            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//            LocalDateTime updatedAt
//    ) {
//        public static SosBoardList of(SosBoard sosBoard) {
//            return SosBoardList.builder()
//                    .id(sosBoard.getId())
//                    .userId(sosBoard.getUser().getId())
//                    .accountId(sosBoard.getUser().getAccountId())
//                    .title(sosBoard.getTitle())
//                    .comment(sosBoard.getComment())
//                    .paymentType(sosBoard.getPaymentType())
//                    .price(sosBoard.getPrice())
//                    .petIds(sosBoard.getPetSosBoards().stream().map(petSosBoard -> petSosBoard.getPet().getId()).toList())
//                    .petInfos(sosBoard.getPetSosBoards().stream().map(petSosBoard -> SosBoardPetInfo.of(petSosBoard.getPet())).toList())
//                    .startDate(sosBoard.getStartDate())
//                    .endDate(sosBoard.getEndDate())
//                    .userNickname(sosBoard.getUser().getNickname())
//                    .userProfileImg(sosBoard.getUser().getProfileImg())
//                    .userGender(sosBoard.getUser().getGender().toString())
//                    .userRegion(sosBoard.getUser().getRegion())
//                    .createdAt(sosBoard.getCreatedAt())
//                    .updatedAt(sosBoard.getUpdatedAt())
//                    .build();
//        }
//
//    }
//
//    public static SosBoardInfoList of(List<SosBoard> sosBoards, long totalCount, long totalPages, long currentPage ) {
//        return new SosBoardInfoList(sosBoards.stream().map(SosBoardList::of).toList(),totalCount,totalPages,currentPage);
//
//    }
//}
