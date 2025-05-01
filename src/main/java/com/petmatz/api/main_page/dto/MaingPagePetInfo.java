//package com.petmatz.api.main_page.dto;
//
//import com.petmatz.domain.old.petmission.entity.PetToPetMissionEntity;
//import com.petmatz.domain.pet.PetGender;
//import com.petmatz.domain.pet.entity.Pet;
//import lombok.Builder;
//
//@Builder
//public record MaingPagePetInfo(
//
//        Long petId,
//        String petName,
//        String breed,
//        Integer age,
//        PetGender petGender,
//        String neuterYn,
//        String temperament,
//        String profileImg
//
//) {
//
//    public static MaingPagePetInfo of(PetToPetMissionEntity pet) {
//        Pet petInfo = pet.getPet();
//        return MaingPagePetInfo.builder()
//                .petId(petInfo.getId())
//                .petName(petInfo.getPetName())
//                .breed(petInfo.getBreed())
//                .age(petInfo.getAge())
//                .gender(petInfo.getPetGender())
//                .neuterYn(petInfo.getNeuterYn())
//                .temperament(petInfo.getTemperament())
//                .profileImg(petInfo.getProfileImg())
//                .build();
//    }
//
//}
