package com.petmatz.domain.sosboard.dto;

import com.petmatz.domain.pet.entity.Pet;
import lombok.Builder;

@Builder
public record SosBoardPet(
        Long id,
        String petName,
        String breed,
        String gender,
        String neuterYn, // 중성 여부
        String size,
        Integer age,
        String temperament,
        String preferredWalkingLocation,
        String profileImg,
        String comment
) {
    // 정적 팩토리 메서드: Pet → SosBoardPetDto
    public static SosBoardPet of(Pet pet) {
        return SosBoardPet.builder()
                .id(pet.getId())
                .petName(pet.getPetName())
                .breed(pet.getBreed())
                .gender(pet.getGender().toString())
                .neuterYn(pet.getNeuterYn())
                .size(pet.getSize().toString())
                .age(pet.getAge())
                .temperament(pet.getTemperament())
                .preferredWalkingLocation(pet.getPreferredWalkingLocation())
                .profileImg(pet.getProfileImg())
                .build();
    }
}

