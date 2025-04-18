package com.petmatz.domain.old.chatting.dto;

import lombok.Builder;

@Builder
public record ChatRoomInfo(

        String caregiverInfo,
        String entrustedInfo

) {
}
