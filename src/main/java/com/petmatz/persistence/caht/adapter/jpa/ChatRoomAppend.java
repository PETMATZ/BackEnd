//package com.petmatz.persistence.caht.adapter.jpa;
//
//import com.petmatz.application.chat.dto.ChatRoomInfo;
//import com.petmatz.persistence.caht.entity.ChatRoomEntity;
//import com.petmatz.persistence.caht.entity.UserToChatRoomEntity;
//import com.petmatz.persistence.caht.repository.ChatRoomRepository;
//import com.petmatz.domain.user.User;
//import com.petmatz.domain.user.port.UserQueryPort;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class ChatRoomAppend {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    private final UserQueryPort userQueryPort;
//
//    //신규 채팅방 생성
//    public long append(ChatRoomInfo chatRoomInfo) {
//        User user1 = userQueryPort.findAccountIdByUserInfo(chatRoomInfo.caregiverInfo());
//        User user2 = userQueryPort.findAccountIdByUserInfo(chatRoomInfo.entrustedInfo());
//
//
//        UserToChatRoomEntity userToChatRoomEntity1 = new UserToChatRoomEntity();
//        userToChatRoomEntity1.addUser(user1);
//
//        UserToChatRoomEntity userToChatRoomEntity2 = new UserToChatRoomEntity();
//        userToChatRoomEntity2.addUser(user2);
//
//        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
//        chatRoomEntity.addParticipant(userToChatRoomEntity1);
//        chatRoomEntity.addParticipant(userToChatRoomEntity2);
//
//
//        ChatRoomEntity saveChatRoomEntity = chatRoomRepository.save(chatRoomEntity);
//        return saveChatRoomEntity.getId();
//    }
//}
