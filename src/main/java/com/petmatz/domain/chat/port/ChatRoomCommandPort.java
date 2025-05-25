package com.petmatz.domain.chat.port;

import com.petmatz.application.chat.dto.ChatRoomInfo;

public interface ChatRoomCommandPort {

    long save(ChatRoomInfo chatRoomInfo);
}
