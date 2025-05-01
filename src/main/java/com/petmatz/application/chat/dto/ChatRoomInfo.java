package com.petmatz.application.chat.dto;

import lombok.Builder;

@Builder
public record ChatRoomInfo(

        String caregiverInfo,
        String entrustedInfo

) {
}
