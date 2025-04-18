package com.petmatz.domain.old.petmission.dto;

import com.petmatz.api.petmission.dto.PetMissionCommentResponse;
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
}
