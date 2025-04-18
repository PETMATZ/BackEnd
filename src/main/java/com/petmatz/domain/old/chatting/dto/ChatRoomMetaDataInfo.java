package com.petmatz.domain.old.chatting.dto;

import com.petmatz.domain.old.chatting.docs.ChatRoomMetadataDocs;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatRoomMetaDataInfo(

        String chatRoomId,
        String lastMessage,
        LocalDateTime lastMessageTimestamp,
        int messageCount,
        int unreadCount,
        IChatUserInfo iChatUserInfo
) {

    public static ChatRoomMetaDataInfo of(ChatRoomMetadataDocs chatRoomMetadataDocs, int messageCount, int unreadCount, IChatUserInfo iChatUserInfo) {
        return ChatRoomMetaDataInfo.builder()
                .chatRoomId(chatRoomMetadataDocs.getRoom_id())
                .lastMessage(chatRoomMetadataDocs.getLastMessage())
                .lastMessageTimestamp(chatRoomMetadataDocs.getLastMessageTimestamp())
                .messageCount(messageCount)
                .unreadCount(unreadCount)
                .iChatUserInfo(iChatUserInfo)
                .build();
    }


}
