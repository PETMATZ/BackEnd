package com.petmatz.domain.chat;

import lombok.Getter;

@Getter
public class ChatRoom {

    private final Long id;

    private final Long caregiverId;

    private final Long entrustedId;

    public ChatRoom(Long id, Long caregiverId, Long entrustedId) {
        this.id = id;
        this.caregiverId = caregiverId;
        this.entrustedId = entrustedId;
    }

    public String chatRoomIdToString() {
        return String.valueOf(id);
    }

}
