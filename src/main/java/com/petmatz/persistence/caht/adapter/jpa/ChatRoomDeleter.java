package com.petmatz.persistence.caht.adapter.jpa;

import com.petmatz.persistence.caht.repository.UserToChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChatRoomDeleter {

    private final UserToChatRoomRepository userToChatRoomRepository;

    @Transactional
    public void deleteChatRoom(String roomId) {
        userToChatRoomRepository.deleteByChatRoom_Id(Long.valueOf(roomId));
    }
}
