package com.petmatz.application.chat.dto;

import lombok.Builder;

@Builder
public record ChatRoomInfo(

        Long caregiverId,
        Long entrustedId

) {
}
