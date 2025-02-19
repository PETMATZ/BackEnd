package com.petmatz.domain.pet.component;

import com.petmatz.domain.aws.AwsClient;
import com.petmatz.domain.aws.vo.S3Imge;
import com.petmatz.domain.global.S3ImgDataInfo;
import com.petmatz.domain.pet.dto.PetSaveInfo;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.pet.exception.PetErrorCode;
import com.petmatz.domain.pet.exception.PetServiceException;
import com.petmatz.domain.pet.repository.PetRepository;
import com.petmatz.domain.pet.utils.PetMapper;
import com.petmatz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
@RequiredArgsConstructor
public class PetAppend {

    private final AwsClient awsClient;
    private final PetRepository petRepository;

    public S3ImgDataInfo savePet(User user, PetSaveInfo petSaveInfo) throws MalformedURLException {
        if (petRepository.existsByDogRegNo(petSaveInfo.dogRegNo())) {
            throw new PetServiceException(PetErrorCode.DOG_REG_NO_DUPLICATE);
        }

        //6-1 Img 정제
        S3Imge petImg = awsClient.UploadImg(user.getAccountId(), petSaveInfo.profileImg(), "PET_IMG", petSaveInfo.dogRegNo());
        //Pet Entity 생성
        Pet pet = PetMapper.of(petSaveInfo, petImg.uploadURL(), user);

        //Pet 정보 저장
        Pet petEntity = petRepository.save(pet);
        Long id = petEntity.getId();

        // User의 isRegistered 상태 업데이트
        user.updateUserRegistered();

//        return S3ImgDataInfo.of(id, resultImgURL);
        return S3ImgDataInfo.of(id, petImg.checkResultImg());
    }

}
