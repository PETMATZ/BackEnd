package com.petmatz.persistence.caht.mapper;

import com.petmatz.domain.chat.ChatReadStatus;
import com.petmatz.domain.chat.ChatRoom;
import com.petmatz.persistence.caht.entity.ChatRoomEntity;
import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;

public class ChatDomainMapper {
    public static ChatRoom to(ChatRoomEntity chatRoomEntity) {
        return new ChatRoom(chatRoomEntity.getId(), chatRoomEntity.getCaregiverId(), chatRoomEntity.getEntrustedId());
    }

    public static ChatReadStatus to(ChatReadStatusDocs chatReadStatusDocs) {
        return new ChatReadStatus(
                chatReadStatusDocs.getChatRoomId(),
                chatReadStatusDocs.getUserId(),
                chatReadStatusDocs.getLastReadMessageId(),
                chatReadStatusDocs.getLastReadTimestamp()
        );
    }

}
