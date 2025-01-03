package com.petmatz.domain.petmission.dto;

import com.petmatz.api.petmission.dto.PetMissionCommentResponse;
import com.petmatz.domain.petmission.entity.PetMissionAnswerEntity;
import com.petmatz.domain.petmission.entity.PetMissionAskEntity;
import lombok.Builder;

@Builder
public record PetMissionAnswerInfo(
        Long id,

        String comment,

        String imgURL


) {

    public PetMissionCommentResponse of() {
        return PetMissionCommentResponse.builder()
                .id(id)
                .comment(comment)
                .imgURL(imgURL)
                .build();
    }

    public static PetMissionAnswerInfo of(PetMissionAskEntity petMissionAskEntity) {
        PetMissionAnswerEntity missionAnswer = petMissionAskEntity.getMissionAnswer();
        return PetMissionAnswerInfo.builder()
                .id(missionAnswer.getId())
                .comment(missionAnswer.getComment())
                .imgURL(missionAnswer.getImgURL())
                .build();
    }
}
