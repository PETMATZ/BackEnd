//package com.petmatz.domain.old.petmission.component;
//
//import com.petmatz.domain.old.petmission.entity.PetMissionAskEntity;
//import com.petmatz.domain.old.petmission.repository.PetMissionAskRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class PetMissionAskReader {
//
//    private final PetMissionAskRepository petMissionAskRepository;
//
//
//    public PetMissionAskEntity selectPetMissionAskInfo(String askId) {
//        Optional<PetMissionAskEntity> petMissionAskEntity = petMissionAskRepository.findById(Long.valueOf(askId));
//        return petMissionAskEntity.orElseThrow(() ->
//                new IllegalArgumentException("Value is missing")
//        );
//    }
//}
