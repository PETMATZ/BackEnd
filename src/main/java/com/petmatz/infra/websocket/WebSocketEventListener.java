package com.petmatz.infra.websocket;

import com.petmatz.persistence.caht.adapter.mongo.ChatReadStatusUpdater;
import com.petmatz.infra.websocket.exception.RegularExpressionNotMatchException;
import com.petmatz.infra.websocket.exception.SubscriptionUrlNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final static Pattern R = Pattern.compile("/topic/chat/(\\S+)/(\\S+)");

    
    //TODO 여기 의존 바꿔야 할 수도
    private final ChatReadStatusUpdater chatReadStatusUpdater;

    //TODO 구독이 끊기면 unreadCount 초기화
    @EventListener
    public void handleUnsubscribeEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String subscriptionURL = accessor.getSubscriptionId();
        if (subscriptionURL == null) {
            throw SubscriptionUrlNotFountException.EXCEPTION;
        }
        Matcher matcher = R.matcher(subscriptionURL);
        if (matcher.matches()) {
            String chatRoomId = matcher.group(1);
            String userEmail = matcher.group(2);

            chatReadStatusUpdater.updateMessageStatusTime(chatRoomId, userEmail);
        }else {
            throw RegularExpressionNotMatchException.EXCEPTION;
        }

    }
}
