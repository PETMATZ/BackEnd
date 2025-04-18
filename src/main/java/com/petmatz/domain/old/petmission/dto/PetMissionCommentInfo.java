package com.petmatz.domain.old.petmission.dto;

import lombok.Builder;

@Builder
public record PetMissionCommentInfo(

        String askId,
        String missionAskId,
        String comment,
        String imgURL

) {

}
