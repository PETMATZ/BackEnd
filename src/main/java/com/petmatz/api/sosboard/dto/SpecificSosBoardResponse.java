package com.petmatz.api.sosboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petmatz.domain.sosboard.PaymentType;
import com.petmatz.domain.sosboard.dto.SpecificSosBoardInfo;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SpecificSosBoardResponse(
        Long id, //게시글 id
        Long userId, //유저 id
        String accountId,
        String title,
        String comment,
        PaymentType paymentType,
        Integer price,
        List<SosBoardPetResponse> petInfos, // PetResponse를 포함
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

        public static SpecificSosBoardResponse of(SpecificSosBoardInfo specificSosBoardInfo) {
                return SpecificSosBoardResponse.builder()
                        .id(specificSosBoardInfo.id())
                        .userId(specificSosBoardInfo.userId())
                        .accountId(specificSosBoardInfo.accountId())
                        .title(specificSosBoardInfo.title())
                        .comment(specificSosBoardInfo.comment())
                        .paymentType(specificSosBoardInfo.paymentType())
                        .price(specificSosBoardInfo.price())
                        .petInfos(specificSosBoardInfo.petInfos().stream().map(SosBoardPetResponse::of).toList())
                        .startDate(specificSosBoardInfo.startDate())
                        .endDate(specificSosBoardInfo.endDate())
                        .authorNickname(specificSosBoardInfo.authorNickname())
                        .authorProfileImg(specificSosBoardInfo.authorProfileImg())
                        .authorGender(specificSosBoardInfo.authorGender())
                        .authorRegion(specificSosBoardInfo.authorRegion())
                        .createdAt(specificSosBoardInfo.createdAt())
                        .updatedAt(specificSosBoardInfo.updatedAt())
                        .build();
        }
}
