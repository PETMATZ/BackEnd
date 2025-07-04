//package com.petmatz.domain.old.sosboard.dto;
//
//import com.petmatz.domain.pet.entity.Pet;
//
//public record SosBoardPet(
//        Long id,
//        String petName,
//        String breed,
//        String gender,
//        String neuterYn, // 중성 여부
//        String size,
//        Integer age,
//        String temperament,
//        String preferredWalkingLocation,
//        String profileImg,
//        String comment
//) {
//    // 정적 팩토리 메서드: Pet → SosBoardPetDto
//    public static SosBoardPet of(Pet pet) {
//        return new SosBoardPet(
//                pet.getId(),
//                pet.getPetName(),
//                pet.getBreed(),
//                pet.getPetGender().toString(),
//                pet.getNeuterYn(), // 중성 여부
//                pet.getSize().toString(),
//                pet.getAge(),
//                pet.getTemperament(),
//                pet.getPreferredWalkingLocation(),
//                pet.getProfileImg(),
//                pet.getComment()
//        );
//    }
//}
//
