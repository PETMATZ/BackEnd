//package com.petmatz.domain.pet;
//
//import com.petmatz.persistence.global.S3ImgDataInfo;
//import com.petmatz.domain.pet.component.OpenApiPet;
//import com.petmatz.domain.pet.component.PetAppend;
//import com.petmatz.domain.pet.component.PetDelete;
//import com.petmatz.domain.pet.component.PetUpdater;
//import com.petmatz.domain.pet.dto.OpenApiPetInfo;
//import com.petmatz.domain.pet.dto.PetSaveInfo;
//import com.petmatz.domain.pet.dto.PetUpdateInfo;
//import com.petmatz.garbege.service.user.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.net.MalformedURLException;
//
//@Service
//@RequiredArgsConstructor
//public class PetService {
//
//    private final OpenApiPet openApiPet;
//    private final PetDelete petDelete;
//    private final PetAppend petAppend;
//    private final PetUpdater petUpdater;
//
//    //펫 등록번호 조회
//    public OpenApiPetInfo fetchPetInfo(String dogRegNo, String ownerNm) {
//        return openApiPet.getOpenApiPetInfo(dogRegNo, ownerNm);
//    }
//
//    //펫 삭제
//    @Transactional
//    public void deletePet(Long petId, User user) {
//        petDelete.deletePet(petId, user);
//    }
//
//    // 펫 저장
//    @Transactional
//    public S3ImgDataInfo savePet(User user, PetSaveInfo petSaveInfo) throws MalformedURLException {
//        return petAppend.savePet(user, petSaveInfo);
//    }
//
//    // 펫 업데이트
//    @Transactional
//    public S3ImgDataInfo updatePet(Long petId, User user, PetUpdateInfo petUpdateInfo) throws MalformedURLException {
//        return petUpdater.updatePet(petId, user, petUpdateInfo);
//    }
//
//}
//
//
