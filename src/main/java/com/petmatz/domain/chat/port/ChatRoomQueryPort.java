package com.petmatz.domain.chat.port;

import com.petmatz.application.chat.dto.ChatRoomInfo;
import com.petmatz.domain.chat.ChatRoom;
import com.petmatz.persistence.caht.entity.ChatRoomEntity;

import java.util.List;
import java.util.Optional;

public interface ChatRoomQueryPort {

    ChatRoom selectChatRoom(ChatRoomInfo chatRoomInfo);

    List<ChatRoom> findChatRoomNumber(Long userId);
}
