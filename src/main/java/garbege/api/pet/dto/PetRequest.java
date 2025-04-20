//package com.petmatz.api.pet.dto;
//
//import com.petmatz.domain.pet.dto.PetSaveInfo;
//
//public record PetRequest(
//        Long id,
//        String dogRegNo,
//        String ownerNm,
//        String petName,
//        String breed,
//        String gender,
//        String neuterYn,
//        String size, // 사용자가 선택한 사이즈
//        Integer age,
//        String temperament,
//        String preferredWalkingLocation,
//        String profileImg,
//        String comment
//) {
//
//    public PetSaveInfo of() {
//        return PetSaveInfo.builder()
//                .id(id)
//                .dogRegNo(dogRegNo)
//                .ownerNm(ownerNm)
//                .petName(petName)
//                .breed(breed)
//                .gender(gender)
//                .neuterYn(neuterYn)
//                .size(size)
//                .age(age)
//                .temperament(temperament)
//                .preferredWalkingLocation(preferredWalkingLocation)
//                .profileImg(profileImg)
//                .comment(comment)
//                .build();
//    }
//
//}
//
