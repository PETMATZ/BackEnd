package com.petmatz.persistence.caht.mongo;

import com.petmatz.application.chat.dto.ChatMessageInfo;
import com.petmatz.application.chat.dto.ChatRoomInfo;
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
    private final String caregiverInfo;
    //받는이 User Id
    //받는 이의 Info 추가
    private final String entrustedInfo;

    private List<ChatMessageInfo> messages = new ArrayList<>();

    @Builder
    @PersistenceCreator
    public ChatRoomDocs(String id, String caregiverInfo, String entrustedInfo, List<ChatMessageInfo> messages) {
        this.id = id;
        this.caregiverInfo = caregiverInfo;
        this.entrustedInfo = entrustedInfo;
        this.messages = messages;
    }

    public static ChatRoomDocs initChatRoomDocs(ChatRoomInfo chatRoomInfo, long chatRoomID) {
        return ChatRoomDocs.builder()
                .id(String.valueOf(chatRoomID))
                .caregiverInfo(chatRoomInfo.caregiverInfo())
                .entrustedInfo(chatRoomInfo.entrustedInfo())
                .build();
    }

}
