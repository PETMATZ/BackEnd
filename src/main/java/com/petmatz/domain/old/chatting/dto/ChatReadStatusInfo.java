package com.petmatz.domain.old.chatting.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatReadStatusInfo(

        String chatRoomId,
        String userEmail,
        LocalDateTime lastReadMessageData


) {
}
