package com.petmatz.domain.old.petmission.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PetMissionInfo(

//        Long careId,
        Long receiverId,
        List<String> petId,
        LocalDateTime missionStarted,
        LocalDateTime missionEnd,

        List<String> petMissionAskInfo

) {
}
