package com.petmatz.api.sosboard.dto;

import com.petmatz.domain.old.sosboard.dto.SosBoardPetInfo;
import lombok.Builder;

@Builder
public record SosBoardPetResponse(
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

    public static SosBoardPetResponse of(SosBoardPetInfo pet) {
        return SosBoardPetResponse.builder()
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
