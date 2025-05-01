package com.petmatz.api.utils;

import com.petmatz.api.chatting.dto.ChatReadStatusDirect;
import com.petmatz.application.chat.dto.ChatMessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendChatMessage {

    private final static String DESTINATION = "/topic/chat/";

    private final SimpMessagingTemplate simpMessagingTemplate;


    public void sendChatMessage(String chatRoomId, ChatMessageInfo chatMessageInfo) {
        String destination = DESTINATION + chatRoomId;
        simpMessagingTemplate.convertAndSend(destination, chatMessageInfo);
    }

    public void sendChatMessage(String chatRoomId, ChatReadStatusDirect chatReadStatusDirect) {
        String destination = DESTINATION + chatRoomId;
        simpMessagingTemplate.convertAndSend(destination, chatReadStatusDirect);
    }

}
