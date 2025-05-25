package com.petmatz.persistence.caht.mongo;

import com.petmatz.application.chat.dto.ChatRoomInfo;
import com.petmatz.persistence.caht.mongo.vo.ChatMessageInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "chat_rooms")
@Getter
public class ChatRoomDocs {

    @Id
    // 채팅방 ID
    private final String id;
    //보내는이 User Id
    //보내는 이의 Info 추가
    private final Long caregiverId;
    //받는이 User Id
    //받는 이의 Info 추가
    private final Long entrustedId;

    private List<ChatMessageInfo> messages = new ArrayList<>();

    @Builder
    @PersistenceCreator
    public ChatRoomDocs(String id, Long caregiverId, Long entrustedId, List<ChatMessageInfo> messages) {
        this.id = id;
        this.caregiverId = caregiverId;
        this.entrustedId = entrustedId;
        this.messages = messages;
    }

    public static ChatRoomDocs initChatRoomDocs(ChatRoomInfo chatRoomInfo, long chatRoomID) {
        return ChatRoomDocs.builder()
                .id(String.valueOf(chatRoomID))
                .caregiverId(chatRoomInfo.caregiverId())
                .entrustedId(chatRoomInfo.entrustedId())
                .build();
    }

}
