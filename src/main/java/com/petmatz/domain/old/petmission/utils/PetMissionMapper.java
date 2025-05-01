//package com.petmatz.domain.old.petmission.utils;
//
//
//import com.petmatz.domain.pet.entity.Pet;
//import com.petmatz.domain.old.petmission.entity.PetMissionEntity;
//import com.petmatz.domain.old.petmission.entity.PetToPetMissionEntity;
//
//import java.util.List;
//
//public class PetMissionMapper {
//
//    public static List<PetToPetMissionEntity> of(List<Pet> pets, PetMissionEntity petMissionEntity) {
//        return pets.stream()
//                .map(pet -> PetToPetMissionEntity.of(pet, petMissionEntity))
//                .toList();
//    }
//
//}
