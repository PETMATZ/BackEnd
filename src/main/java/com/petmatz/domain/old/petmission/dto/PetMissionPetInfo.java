package com.petmatz.domain.old.petmission.dto;

import com.petmatz.domain.pet.PetGender;
import com.petmatz.domain.pet.Size;
import com.petmatz.domain.pet.entity.Pet;
import lombok.Builder;

@Builder
public record PetMissionPetInfo(

        String petName,
        String breed,
        Integer age,
        PetGender petGender,
        String neuterYn,
        Size size, // 크기
        String temperament, // 성격

        String imgURL
) {

    public static PetMissionPetInfo of(Pet pet) {
        return PetMissionPetInfo.builder()
                .petName(pet.getPetName())
                .breed(pet.getBreed())
                .age(pet.getAge())
                .gender(pet.getPetGender())
                .neuterYn(pet.getNeuterYn())
                .size(pet.getSize())
                .temperament(pet.getTemperament())
                .imgURL(pet.getProfileImg())
                .build();
    }
}
