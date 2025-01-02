package com.petmatz.api.sosboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petmatz.domain.sosboard.PaymentType;
import com.petmatz.domain.sosboard.dto.SosBoardInfoList;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record SosBoardListResponse(

        List<SosBoard> content,
        Long totalCount,
        Long totalPages,
        Long currentPage



) {

    @Builder
    private record SosBoard(
            Long id, //게시글 id
            Long userId, //유저 id
            String accountId,
            String title,
            String comment,
            PaymentType paymentType,
            Integer price,
            List<SosBoardPetResponse> pets, // PetResponse를 포함
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
        private static SosBoard of(SosBoardInfoList.SosBoardList sosBoard) {
            return SosBoard.builder()
                    .id(sosBoard.id())
                    .userId(sosBoard.userId())
                    .accountId(sosBoard.accountId())
                    .title(sosBoard.title())
                    .comment(sosBoard.comment())
                    .paymentType(sosBoard.paymentType())
                    .price(sosBoard.price())
                    .pets(sosBoard.petInfos().stream().map(SosBoardPetResponse::of).toList())
                    .startDate(sosBoard.startDate())
                    .endDate(sosBoard.endDate())
                    .authorNickname(sosBoard.userNickname())
                    .authorProfileImg(sosBoard.userProfileImg())
                    .authorGender(sosBoard.userGender())
                    .authorRegion(sosBoard.userRegion())
                    .createdAt(sosBoard.createdAt())
                    .updatedAt(sosBoard.updatedAt())
                    .build();
        }
    }

    public static SosBoardListResponse of(SosBoardInfoList serviceDtoPageResponse) {
        return new SosBoardListResponse(serviceDtoPageResponse.content().stream().map(SosBoard::of).toList(), serviceDtoPageResponse.totalCount(), serviceDtoPageResponse.totalPages(), serviceDtoPageResponse.currentPage());

    }
}
