package com.petmatz.domain.old.chatting.component;

import com.petmatz.domain.old.chatting.dto.ChatRoomInfo;
import com.petmatz.domain.old.chatting.entity.ChatRoomEntity;
import com.petmatz.domain.old.chatting.entity.UserToChatRoomEntity;
import com.petmatz.domain.old.chatting.repository.ChatRoomRepository;
import com.petmatz.domain.old.chatting.repository.UserToChatRoomRepository;
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
