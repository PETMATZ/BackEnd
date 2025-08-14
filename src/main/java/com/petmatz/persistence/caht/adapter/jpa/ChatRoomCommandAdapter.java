//package com.petmatz.persistence.caht.adapter.jpa;
//
//import com.petmatz.application.chat.dto.ChatRoomInfo;
//import com.petmatz.domain.chat.port.ChatRoomCommandPort;
//import com.petmatz.persistence.caht.entity.ChatRoomEntity;
//import com.petmatz.persistence.caht.mapper.ChatEntityMapper;
//import com.petmatz.persistence.caht.repository.ChatRoomRepository;
//import com.petmatz.domain.user.User;
//import com.petmatz.domain.user.port.UserQueryPort;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//
//@Repository
//@RequiredArgsConstructor
//public class ChatRoomCommandAdapter implements ChatRoomCommandPort {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    private final UserQueryPort userQueryPort;
//
//    @Override
//    //신규 채팅방 생성
//    public long save(ChatRoomInfo chatRoomInfo) {
//        User user1 = userQueryPort.findAccountIdByUserInfo(chatRoomInfo.caregiverInfo());
//        User user2 = userQueryPort.findAccountIdByUserInfo(chatRoomInfo.entrustedInfo());
//
//        ChatRoomEntity chatRoomEntity = ChatEntityMapper.to(user1.getId(), user2.getId());
//
//
//        ChatRoomEntity saveChatRoomEntity = chatRoomRepository.save(chatRoomEntity);
//        return saveChatRoomEntity.getId();
//    }
//}
