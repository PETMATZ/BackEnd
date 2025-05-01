package com.petmatz.api.chatting.dto;

import com.petmatz.application.chat.dto.ChatRoomInfo;

public record MatchRequest(

        String caregiverEmail,
        String entrustedEmail

) {

    public ChatRoomInfo of() {
        return ChatRoomInfo.builder()
                .caregiverInfo(caregiverEmail)
                .entrustedInfo(entrustedEmail)
                .build();
    }
}
