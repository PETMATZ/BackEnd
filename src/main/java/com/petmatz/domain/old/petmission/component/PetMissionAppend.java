//package com.petmatz.domain.old.petmission.component;
//
//import com.petmatz.domain.old.petmission.entity.PetMissionAnswerEntity;
//import com.petmatz.domain.old.petmission.entity.PetMissionEntity;
//import com.petmatz.domain.old.petmission.repository.PetMissionAnswerRepository;
//import com.petmatz.domain.old.petmission.repository.PetMissionRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class PetMissionAppend {
//
//    private final PetMissionAnswerRepository petMissionAnswerRepository;
//    private final PetMissionRepository petMissionRepository;
//
//    public PetMissionAnswerEntity insertPetMissionAnswer(PetMissionAnswerEntity petMissionAnswerEntity) {
//        return petMissionAnswerRepository.save(petMissionAnswerEntity);
//    }
//
//    public void insertPetMission(PetMissionEntity petMission) {
//        petMissionRepository.save(petMission);
//    }
//
//
//
//}
