package com.petmatz.persistence.caht.mongo;

import com.petmatz.application.chat.dto.ChatMessageInfo;
import com.petmatz.application.chat.dto.ChatRoomInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat_room_metadata")
@Getter
public class ChatRoomMetadataDocs {
    @Id
    private final String room_id;
    private final String lastMessage;
    private final LocalDateTime lastMessageTimestamp;
    private final String msg_type;
    private final int messageCount;
    private final int unreadCount;

    @Builder
    @PersistenceCreator
    public ChatRoomMetadataDocs(String room_id, String lastMessage, LocalDateTime lastMessageTimestamp, String msg_type, int messageCount, int unreadCount) {
        this.room_id = room_id;
        this.lastMessage = lastMessage;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.msg_type = msg_type;
        this.messageCount = messageCount;
        this.unreadCount = unreadCount;
    }

    public static ChatRoomMetadataDocs initChatRoomMetaData(ChatRoomInfo chatRoomInfo, long chatRoomId) {
        return ChatRoomMetadataDocs.builder()
                .room_id(String.valueOf(chatRoomId))
                .lastMessage("first")
                .lastMessageTimestamp(LocalDateTime.now())
                .messageCount(0)
                .unreadCount(0)
                .build();
    }

    public static ChatRoomMetadataDocs updateChatRoomMetaData(ChatMessageInfo chatMessageInfo, String chatRoomId) {
        return ChatRoomMetadataDocs.builder()
                .room_id(chatRoomId)
                .lastMessage(chatMessageInfo.getMsg())
                .lastMessageTimestamp(LocalDateTime.now())
                .messageCount(0)
                .unreadCount(0)
                .msg_type(String.valueOf(chatMessageInfo.getMsg_type()))
                .build();
    }
}
