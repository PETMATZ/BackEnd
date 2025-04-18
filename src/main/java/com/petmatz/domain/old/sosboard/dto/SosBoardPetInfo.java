package com.petmatz.domain.old.sosboard.dto;

import com.petmatz.domain.pet.entity.Pet;
import lombok.Builder;

@Builder
public record SosBoardPetInfo(

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

    public static SosBoardPetInfo of(Pet pet) {
        return SosBoardPetInfo.builder()
                .id(pet.getId())
                .dogRegNo(pet.getDogRegNo())
                .dogNm(pet.getPetName())
                .sexNm(pet.getPetGender().toString())
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
