package com.petmatz.persistence.caht.adapter.jpa;

import com.petmatz.application.chat.dto.ChatRoomInfo;
import com.petmatz.persistence.caht.entity.ChatRoomEntity;
import com.petmatz.persistence.caht.entity.UserToChatRoomEntity;
import com.petmatz.persistence.caht.repository.ChatRoomRepository;
import com.petmatz.persistence.caht.repository.UserToChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatRoomReader {

    private final ChatRoomRepository chatRoomRepository;
    private final UserToChatRoomRepository userToChatRoomRepository;

    //TODO 예외처리 해야 함.
    //자기자신이 속한 채팅방을 전부 조회
    public List<Long> findChatRoomNumber(String userEmail) {
        return userToChatRoomRepository.findDistinctByUserAccountId(userEmail);
    }

    public List<UserToChatRoomEntity> selectChatRoomUserList(List<Long> chatRoomIdList, String accountId) {
        return userToChatRoomRepository.selectChatRoomUserList(chatRoomIdList, accountId);
    }

    public Optional<ChatRoomEntity> selectChatRoom(ChatRoomInfo chatRoomInfo) {
        return chatRoomRepository.findChatRoomByUsers(chatRoomInfo.caregiverInfo(), chatRoomInfo.entrustedInfo());
    }

    public List<String> selectChatRoomUserList(String chatRoomId) {
        return userToChatRoomRepository.selectChatRoomForUserList(Long.valueOf(chatRoomId));
    }


}
