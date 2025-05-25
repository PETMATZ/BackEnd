package com.petmatz.domain.chat.port;

import com.petmatz.application.chat.dto.ChatRoomInfo;

public interface ChatDocsInitPort {

    void init(ChatRoomInfo chatRoomInfo, long chatRoomID);

}
