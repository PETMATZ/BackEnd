package com.petmatz.persistence.caht.adapter.jpa;

import com.petmatz.application.chat.dto.ChatRoomInfo;
import com.petmatz.domain.chat.ChatRoom;
import com.petmatz.domain.chat.port.ChatRoomQueryPort;
import com.petmatz.domain.chat.exception.NotFoundChatRoom;
import com.petmatz.domain.user.exception.UserException;
import com.petmatz.persistence.caht.entity.ChatRoomEntity;
import com.petmatz.persistence.caht.mapper.ChatDomainMapper;
import com.petmatz.persistence.caht.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.petmatz.domain.user.exception.UserErrorCode.USER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ChatRoomQueryAdapter implements ChatRoomQueryPort {

    private final ChatRoomRepository chatRoomRepository;

    //TODO 예외처리 해야 함.
    //자기자신이 속한 채팅방을 전부 조회
    @Override
    public List<ChatRoom> findChatRoomNumber(Long userId) {
        List<ChatRoomEntity> chatRoomEntityList = chatRoomRepository.findDistinctByUserAccountId(userId);
        if (chatRoomEntityList.isEmpty()) {
            throw new NotFoundChatRoom();
        }
        return chatRoomEntityList.stream().map(ChatDomainMapper::to).toList();
    }

//
//    public List<UserToChatRoomEntity> selectChatRoomUserList(List<Long> chatRoomIdList, String accountId) {
//        return userToChatRoomRepository.selectChatRoomUserList(chatRoomIdList, accountId);
//    }

    public ChatRoom selectChatRoom(ChatRoomInfo chatRoomInfo) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findChatRoomByUsers(chatRoomInfo.caregiverId(), chatRoomInfo.entrustedId()).orElseThrow(
                () -> new UserException(USER_NOT_FOUND)
        );
        return ChatDomainMapper.to(chatRoomEntity);
    }

//    public List<String> selectChatRoomUserList(String chatRoomId) {
//        return userToChatRoomRepository.selectChatRoomForUserList(Long.valueOf(chatRoomId));
//    }
}
