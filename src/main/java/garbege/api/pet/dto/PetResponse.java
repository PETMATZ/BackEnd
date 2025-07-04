//package com.petmatz.api.pet.dto;
//
//import com.petmatz.domain.pet.entity.Pet;
//import com.petmatz.domain.sosboard.dto.SosBoardPet;
//
//public record PetResponse(
//        Long id,
//        String dogRegNo,
//        String dogNm,
//        String sexNm,
//        String kindNm,
//        String neuterYn,
//        String profileImg,
//        int age,
//        String temperament,
//        String size,
//        String preferredWalkingLocation,
//        String comment
//) {
//
//    public static PetResponse of(SosBoardPet dto) {
//        return new PetResponse(
//                dto.id(),
//                null,                   // dogRegNo (SosBoardPetDto에는 해당 필드가 없을 경우 null)
//                dto.petName(),                    // dogNm
//                dto.gender(),                     // gender
//                dto.breed(),                      // kindNm
//                dto.neuterYn(),                   // neuterYn
//                dto.profileImg(),                 // profileImg
//                dto.age() != null ? dto.age() : 0, // age (null 값 처리)
//                dto.temperament(),                // temperament
//                dto.size(),                       // size
//                dto.preferredWalkingLocation(),   // preferredWalkingLocation
//                dto.comment()
//
//        );
//    }
//
//    // Pet → PetResponse 변환 (새로 추가된 메서드)
//    public static PetResponse of(Pet pet) {
//        return new PetResponse(
//                pet.getId(),
//                pet.getDogRegNo(),
//                pet.getPetName(),
//                pet.getPetGender().toString(),
//                pet.getBreed(),
//                pet.getNeuterYn().equalsIgnoreCase("YES") ? "중성" : "미중성",
//                pet.getProfileImg(),
//                pet.getAge() != null ? pet.getAge() : 0,
//                pet.getTemperament(),
//                pet.getSize().toString(),
//                pet.getPreferredWalkingLocation(),
//                pet.getComment()
//        );
//    }
//}
//
