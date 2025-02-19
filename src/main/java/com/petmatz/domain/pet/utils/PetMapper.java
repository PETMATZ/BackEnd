package com.petmatz.domain.pet.utils;

import com.petmatz.domain.pet.Gender;
import com.petmatz.domain.pet.Size;
import com.petmatz.domain.pet.dto.PetSaveInfo;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.user.entity.User;

public class PetMapper {

    public static Pet of(PetSaveInfo petSaveInfo, String imgURL, User user) {
        return Pet.builder()
                .user(user)
                .dogRegNo(petSaveInfo.dogRegNo())
                .petName(petSaveInfo.petName())
                .breed(petSaveInfo.breed())
                .gender(Gender.fromString(petSaveInfo.gender()))
                .neuterYn(petSaveInfo.neuterYn())
                .size(Size.fromString(petSaveInfo.size()))
                .age(petSaveInfo.age())
                .temperament(petSaveInfo.temperament())
                .preferredWalkingLocation(petSaveInfo.preferredWalkingLocation())
                .profileImg(imgURL)
                .comment(petSaveInfo.comment())
                .build();
    }

}
