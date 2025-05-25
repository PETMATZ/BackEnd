package com.petmatz.persistence.caht.mongo;

import com.petmatz.application.chat.support.ChatUtils;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat_read_status")
@Getter
public class ChatReadStatusDocs {

    @Id
    private final String chatRoomId;
    private final Long userId;
    private final String lastReadMessageId;
    private final LocalDateTime lastReadTimestamp;

    @Builder
    @PersistenceCreator
    public ChatReadStatusDocs(String chatRoomId, Long userId, String lastReadMessageId, LocalDateTime lastReadTimestamp) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.lastReadMessageId = lastReadMessageId;
        this.lastReadTimestamp = lastReadTimestamp;
    }

    public static ChatReadStatusDocs initChatReadStatusData(Long userId, long chatRoomID) {
        return ChatReadStatusDocs.builder()
                .lastReadTimestamp(LocalDateTime.now())
                .chatRoomId(ChatUtils.addString(String.valueOf(chatRoomID), userId))
                .userId(userId)
                .build();
    }


}
