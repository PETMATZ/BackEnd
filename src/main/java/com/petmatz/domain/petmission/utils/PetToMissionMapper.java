package com.petmatz.domain.petmission.utils;

import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.petmission.entity.PetMissionEntity;
import com.petmatz.domain.petmission.entity.PetToPetMissionEntity;

public class PetToMissionMapper {

    public static PetToPetMissionEntity of(Pet pet, PetMissionEntity petMissionEntity) {
        return PetToPetMissionEntity.builder()
                .pet(pet)
                .petMission(petMissionEntity)
                .build();
    }

}
