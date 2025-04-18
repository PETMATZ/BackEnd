package com.petmatz.domain.old.petmission;

import com.petmatz.api.petmission.dto.PetMissionUpdateRequest;
import com.petmatz.domain.old.aws.AwsClient;
import com.petmatz.domain.old.aws.vo.S3Imge;
import com.petmatz.domain.old.petmission.component.*;
import com.petmatz.domain.old.petmission.dto.*;
import com.petmatz.domain.old.petmission.entity.*;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.pet.repository.PetRepository;
import com.petmatz.domain.petmission.component.*;
import com.petmatz.domain.petmission.dto.*;
import com.petmatz.domain.petmission.entity.*;
import com.petmatz.domain.old.petmission.exception.ExistPetMissionAnswerException;
import com.petmatz.domain.old.petmission.utils.PetMissionMapper;
import garbege.service.user.provider.UserUtils;
import com.petmatz.garbege.service.user.User;
import com.petmatz.persistence.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PetMissionService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;

    private final UserToChatRoomReader userToChatRoomReader;
    private final UserToPetMissionAppend userToPetMissionAppend;
    private final UserToPetMissionReader userToPetMissionReader;
    private final PetMissionReader petMissionReader;
    private final PetMissionAppend petMissionAppend;
    private final PetMissionAskReader petMissionAskReader;
    private final AwsClient awsClient;
    private final UserUtils userUtils;


    //TODO 이건 chatRoom으로 옮겨져야 하는데 왜 여기?
    public String selectChatRoomId(String careEmail, String receiverEmail) {
        return userToChatRoomReader.selectChatRoomId(careEmail, receiverEmail);
    }
    @Transactional
    public PetMissionData insertPetMission(PetMissionInfo petMissionInfo, Long careId) {

        User careUser = userUtils.findIdUser(careId);
        User receiverUser = userUtils.findIdUser(petMissionInfo.receiverId());

        String chatRoomId = userToChatRoomReader.selectChatRoomId(careUser.getAccountId(), receiverUser.getAccountId());

        List<Pet> pets = petRepository.findPetListByPetId(petMissionInfo.petId());
        if (pets.isEmpty()) {
            throw new IllegalArgumentException("해당 Pet ID에 대한 펫을 찾을 수 없습니다");
        }

        List<PetMissionAskEntity> petMissionAskEntityList = petMissionInfo.petMissionAskInfo()
                .stream()
                .map(PetMissionAskEntity::of)
                .toList();

        PetMissionEntity petMissionEntity = PetMissionEntity.of(petMissionInfo);
        petMissionEntity.addPetMissionAsk(petMissionAskEntityList);

        List<PetToPetMissionEntity> petToPetMissionEntities = PetMissionMapper.of(pets, petMissionEntity);

//        List<PetToPetMissionEntity> petToPetMissionEntities = pets.stream()
//                .map(pet -> PetToPetMissionEntity.of(pet, petMissionEntity))
//                .toList();

        petToPetMissionEntities.forEach(petMissionEntity::addPetToPetMission);

        List<UserToPetMissionEntity> userToPetMissionEntities = Stream.of(careUser, receiverUser).map(user -> UserToPetMissionEntity.of(user, petMissionEntity, careId))
                .toList();

        petMissionAppend.insertPetMission(petMissionEntity);
        userToPetMissionAppend.insertUserToPetMission(userToPetMissionEntities);

        return PetMissionData.of(chatRoomId, petMissionEntity);
    }

    public List<UserToPetMissionEntity> selectPetMissionList(Long userId) {
        return userToPetMissionReader.selectUserToPetMissionList(userId);
    }

    public List<UserToPetMissionEntity> selectPetMissionList(Long userId, LocalDate petMissionStart) {
        return userToPetMissionReader.selectUserToPetMissionList(userId, petMissionStart);
    }

    @Transactional
    public void updatePetMissionStatus(PetMissionUpdateRequest petMissionUpdateRequest) {
        List<UserToPetMissionEntity> userToPetMissionEntities = userToPetMissionReader.selectUserToPetMissionList(petMissionUpdateRequest);
        for (UserToPetMissionEntity userToPetMissionEntity : userToPetMissionEntities) {
            PetMissionEntity petMission = userToPetMissionEntity.getPetMission();
            petMission.updatePetMissionStatusZip(petMissionUpdateRequest.missionStatusZip());
        }
    }

    public List<UserToPetMissionEntity> selectUserToPetMissionList(String petMissionId) {
        return userToPetMissionReader.selectUserToPetMissionList(petMissionId);
    }

    public PetMissionDetails selectPetMissionInfo(String petMissionId) {
        List<UserToPetMissionEntity> userToPetMissionEntities = userToPetMissionReader.selectUserToPetMissionList(petMissionId);

        PetMissionEntity petMissionEntity = petMissionReader.selectUserToPetMission(petMissionId);

        return PetMissionDetails.of(petMissionEntity, userToPetMissionEntities);
    }

    @Transactional
    public String updatePetMissionComment(PetMissionCommentInfo petMissionCommentInfo, String userEmail) throws MalformedURLException {
        PetMissionAskEntity petMissionAskEntity = petMissionReader.selectById(Long.valueOf(petMissionCommentInfo.askId()));
        if (petMissionAskEntity.getMissionAnswer() != null) {
            throw ExistPetMissionAnswerException.EXCEPTION;
        }

        S3Imge petImg = awsClient.UploadImg(userEmail, petMissionCommentInfo.imgURL(), "CARE_HISTORY_IMG", String.valueOf(petMissionAskEntity.getId()));

        //6-1 Img 정제
        PetMissionAnswerEntity petMissionAnswerEntity = petMissionAppend.insertPetMissionAnswer(PetMissionAnswerEntity.of(petMissionCommentInfo, petImg.uploadURL()));
        petMissionAskEntity.addPetMissionAnswer(petMissionAnswerEntity);
        return petImg.checkResultImg();
    }

    public PetMissionAnswerInfo selectPetMissionAnswerInfo(String askId) {
        PetMissionAskEntity petMissionAskEntity = petMissionAskReader.selectPetMissionAskInfo(askId);
        if (petMissionAskEntity.getMissionAnswer() == null) {
            return PetMissionAnswerInfo.builder().build();
        }
        return petMissionAskEntity.getMissionAnswer().of();
    }
}

