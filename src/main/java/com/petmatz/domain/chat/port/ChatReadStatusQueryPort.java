package com.petmatz.domain.chat.port;

import com.petmatz.domain.chat.ChatReadStatus;
import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;

public interface ChatReadStatusQueryPort {

    ChatReadStatus selectChatMessageLastStatus(String chatRoomId, Long userId);

}
