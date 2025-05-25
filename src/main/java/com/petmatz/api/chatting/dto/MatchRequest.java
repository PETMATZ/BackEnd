package com.petmatz.api.chatting.dto;

import com.petmatz.application.chat.dto.ChatRoomInfo;
import jakarta.validation.constraints.NotNull;

public record MatchRequest(

        @NotNull
        Long caregiverId,
        @NotNull
        Long entrustedId

) {

    public ChatRoomInfo of() {
        return ChatRoomInfo.builder()
                .caregiverId(caregiverId)
                .entrustedId(entrustedId)
                .build();
    }
}
