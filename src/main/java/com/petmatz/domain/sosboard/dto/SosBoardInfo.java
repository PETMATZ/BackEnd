package com.petmatz.domain.sosboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petmatz.api.pet.dto.PetResponse;
import com.petmatz.domain.pet.dto.PetInf;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.sosboard.PaymentType;
import com.petmatz.domain.sosboard.entity.PetSosBoard;
import com.petmatz.domain.sosboard.entity.SosBoard;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record SosBoardInfo(

        List<SosBoardList> content,
        Long totalCount,
        Long totalPages,
        Long currentPage



) {

    @Builder
    public record SosBoardList(
            Long id, //게시글 id
            Long userId, //유저 id
            String accountId,
            String title,
            String comment,
            PaymentType paymentType,
            Integer price,
            List<Long> petIds,
            List<PetInfo> petInfos, // PetResponse를 포함
            String startDate, // 변환된 LocalDateTime
            String endDate,    // 변환된 LocalDateTime
            String userNickname,
            String userProfileImg,
            String userGender,
            String userRegion,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
            LocalDateTime createdAt, // 생성일시
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
            LocalDateTime updatedAt
    ) {
        public static SosBoardList of(SosBoard sosBoard) {
            return SosBoardList.builder()
                    .id(sosBoard.getId())
                    .userId(sosBoard.getUser().getId())
                    .accountId(sosBoard.getUser().getAccountId())
                    .title(sosBoard.getTitle())
                    .comment(sosBoard.getComment())
                    .paymentType(sosBoard.getPaymentType())
                    .price(sosBoard.getPrice())
                    .petIds(sosBoard.getPetSosBoards().stream().map(petSosBoard -> petSosBoard.getPet().getId()).toList())
                    .petInfos(sosBoard.getPetSosBoards().stream().map(petSosBoard -> PetInfo.of(petSosBoard.getPet())).toList())
                    .startDate(sosBoard.getStartDate())
                    .endDate(sosBoard.getEndDate())
                    .userNickname(sosBoard.getUser().getNickname())
                    .userProfileImg(sosBoard.getUser().getProfileImg())
                    .userGender(sosBoard.getUser().getGender().toString())
                    .userRegion(sosBoard.getUser().getRegion())
                    .createdAt(sosBoard.getCreatedAt())
                    .updatedAt(sosBoard.getUpdatedAt())
                    .build();
        }

    }

    @Builder
    public record PetInfo(
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
        public static PetInfo of(Pet pet) {
            return PetInfo.builder()
                    .id(pet.getId())
                    .dogRegNo(pet.getDogRegNo())
                    .dogNm(pet.getPetName())
                    .sexNm(pet.getGender().toString())
                    .kindNm(pet.getBreed())
                    .neuterYn(pet.getNeuterYn().equalsIgnoreCase("YES") ? "중성" : "미중성")
                    .profileImg(pet.getProfileImg())
                    .age(pet.getAge())
                    .temperament(pet.getTemperament())
                    .size(String.valueOf(pet.getSize()))
                    .preferredWalkingLocation(pet.getPreferredWalkingLocation())
                    .comment(pet.getComment())
                    .build();
        }
    }

    public static SosBoardInfo of(List<SosBoard> sosBoards, long totalCount, long totalPages, long currentPage ) {
        return new SosBoardInfo(sosBoards.stream().map(SosBoardList::of).toList(),totalCount,totalPages,currentPage);

    }
}
