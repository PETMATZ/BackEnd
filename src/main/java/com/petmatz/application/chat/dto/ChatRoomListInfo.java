package com.petmatz.application.chat.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatRoomListInfo(

        String chatRoomId,
        String chatRoomName,
        String lastMessage,
        LocalDateTime lastMessageTimestamp,
        int messageCount,
        int unreadCount


) {
}
