package com.petmatz.domain.sosboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.sosboard.PaymentType;
import com.petmatz.domain.sosboard.entity.SosBoard;
import com.petmatz.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SosBoardInfo(

        Long id, //게시글 id
        Long userId, //유저 id
        String accountId,
        String title,
        String comment,
        PaymentType paymentType,
        Integer price,
        List<SosBoardPetInfo> petInfos, // PetResponse를 포함
        String startDate, // 변환된 LocalDateTime
        String endDate,    // 변환된 LocalDateTime
        String authorNickname,
        String authorProfileImg,
        String authorGender,
        String authorRegion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        LocalDateTime createdAt, // 생성일시
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        LocalDateTime updatedAt
) {

    public static SosBoardInfo of(SosBoard savedBoard, List<Pet> petList) {
        User user = savedBoard.getUser();
        return SosBoardInfo.builder()
                .id(savedBoard.getId())
                .userId(user.getId())
                .accountId(user.getAccountId())
                .title(savedBoard.getTitle())
                .comment(savedBoard.getComment())
                .paymentType(savedBoard.getPaymentType())
                .price(savedBoard.getPrice())
                .startDate(savedBoard.getStartDate())
                .endDate(savedBoard.getEndDate())
                .petInfos(petList.stream().map(SosBoardPetInfo::of).toList())
                .authorNickname(user.getNickname())
                .authorProfileImg(user.getProfileImg())
                .authorGender(user.getGender().toString())
                .authorRegion(user.getRegion())
                .createdAt(savedBoard.getCreatedAt())
                .updatedAt(savedBoard.getUpdatedAt())
                .build();

    }
}
