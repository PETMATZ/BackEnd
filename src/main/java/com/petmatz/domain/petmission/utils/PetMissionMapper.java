package com.petmatz.domain.petmission.utils;

import com.petmatz.common.constants.PetMissionStatusZip;
import com.petmatz.domain.petmission.dto.PetMissionCommentInfo;
import com.petmatz.domain.petmission.dto.PetMissionInfo;
import com.petmatz.domain.petmission.entity.PetMissionAnswerEntity;
import com.petmatz.domain.petmission.entity.PetMissionAskEntity;
import com.petmatz.domain.petmission.entity.PetMissionEntity;

public class PetMissionMapper {

    public static PetMissionEntity of(PetMissionInfo petMissionInfo) {
        return PetMissionEntity.builder()
                .petMissionStarted(petMissionInfo.missionStarted())
                .petMissionEnd(petMissionInfo.missionEnd())
                .status(PetMissionStatusZip.BEF)
                .build();
    }

    public static PetMissionAnswerEntity of(PetMissionCommentInfo petMissionCommentInfo, String imgURL) {
        return PetMissionAnswerEntity.builder()
                .comment(petMissionCommentInfo.comment())
                .imgURL(imgURL)
                .build();
    }

    public static PetMissionAskEntity of(String comment) {
        return PetMissionAskEntity.builder()
                .comment(comment)
                .build();
    }

}
