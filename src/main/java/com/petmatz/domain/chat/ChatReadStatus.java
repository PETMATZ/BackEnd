package com.petmatz.domain.chat;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatReadStatus {

    private final String chatRoomId;
    private final Long userId;
    private final String lastReadMessageId;
    private final LocalDateTime lastReadTimestamp;

    public ChatReadStatus(String chatRoomId, Long userId, String lastReadMessageId, LocalDateTime lastReadTimestamp) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.lastReadMessageId = lastReadMessageId;
        this.lastReadTimestamp = lastReadTimestamp;
    }


    public LocalDateTime checkLastReadTimestamp() {
        return this.chatRoomId != null ? this.lastReadTimestamp : null;
    }
}
