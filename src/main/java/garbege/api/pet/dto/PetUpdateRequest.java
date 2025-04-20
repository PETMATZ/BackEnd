//package com.petmatz.api.pet.dto;
//
//import com.petmatz.domain.pet.PetGender;
//import com.petmatz.domain.pet.Size;
//import com.petmatz.domain.pet.dto.PetUpdateInfo;
//
//
//public record PetUpdateRequest(
//
//        int age,
//        String breed,
//        String comment,
//        PetGender petGender,
//        String neuterYn,
//        String petName,
//        String profileImg,
//        Size size, // 사용자가 선택한 사이즈
//        String temperament
//
//) {
//
//    public PetUpdateInfo of() {
//        return PetUpdateInfo.builder()
//                .age(age)
//                .breed(breed)
//                .comment(comment)
//                .gender(petGender)
//                .neuterYn(neuterYn)
//                .petName(petName)
//                .profileImg(profileImg)
//                .size(size)
//                .temperament(temperament)
//                .build();
//    }
//}
