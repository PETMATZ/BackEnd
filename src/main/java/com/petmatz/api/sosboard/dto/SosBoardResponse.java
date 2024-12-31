package com.petmatz.api.sosboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.sosboard.PaymentType;
import com.petmatz.domain.sosboard.dto.SosBoardInfo;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record SosBoardResponse(

        List<SosBoardListResponse> content,
        Long totalCount,
        Long totalPages,
        Long currentPage



) {

    @Builder
    public record SosBoardListResponse(
            Long id, //게시글 id
            Long userId, //유저 id
            String accountId,
            String title,
            String comment,
            PaymentType paymentType,
            Integer price,
            List<PetResponse> pets, // PetResponse를 포함
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
        public static SosBoardListResponse of(SosBoardInfo.SosBoardList sosBoard) {
            return SosBoardListResponse.builder()
                    .id(sosBoard.id())
                    .userId(sosBoard.userId())
                    .accountId(sosBoard.accountId())
                    .title(sosBoard.title())
                    .comment(sosBoard.comment())
                    .paymentType(sosBoard.paymentType())
                    .price(sosBoard.price())
                    .pets(sosBoard.petInfos().stream().map(PetResponse::of).toList())
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

    @Builder
    public record PetResponse(
            Long id,
            String dogRegNo,
            String dogNm,
            String sexNm,
            String kindNm,
            String neuterYn,
            String profileImg,
            int age,
            String temperament,
            String size,
            String preferredWalkingLocation,
            String comment
    ) {
        public static PetResponse of(SosBoardInfo.PetInfo pet) {
            return PetResponse.builder()
                    .id(pet.id())
                    .dogRegNo(pet.dogRegNo())
                    .dogNm(pet.dogNm())
                    .sexNm(pet.sexNm())
                    .kindNm(pet.kindNm())
                    .neuterYn(pet.neuterYn())
                    .profileImg(pet.profileImg())
                    .age(pet.age())
                    .temperament(pet.temperament())
                    .size(pet.size())
                    .preferredWalkingLocation(pet.preferredWalkingLocation())
                    .comment(pet.comment())
                    .build();
        }
    }

    public static SosBoardResponse of(SosBoardInfo serviceDtoPageResponse) {
        return new SosBoardResponse(serviceDtoPageResponse.content().stream().map(
                SosBoardListResponse::of).toList(), serviceDtoPageResponse.totalCount(), serviceDtoPageResponse.totalPages(), serviceDtoPageResponse.currentPage());

    }
}
