package com.petmatz.domain.chatting;

import com.petmatz.domain.chatting.component.*;
import com.petmatz.domain.chatting.docs.ChatReadStatusDocs;
import com.petmatz.domain.chatting.dto.ChatRoomInfo;
import com.petmatz.domain.chatting.dto.ChatRoomMetaDataInfo;
import com.petmatz.domain.chatting.dto.IChatUserInfo;
import com.petmatz.domain.chatting.entity.ChatRoomEntity;
import com.petmatz.domain.chatting.entity.UserToChatRoomEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomAppend chatRoomAppend;
    private final ChatDocsAppend chatDocsAppend;

    private final ChatRoomReader chatRoomReader;
    private final ChatMessageReader chatMessageReader;
    private final ChatRoomMetaDataReader chatRoomMetaDataReader;
    private final ChatReadStatusReader chatReadStatusReader;

    private final ChatRoomDeleter chatRoomDeleter;
    private final ChatMessageDeleter chatMessageDeleter;
    private final ChatRoomMetaDataDeleter chatRoomMetaDataDeleter;
    private final ChatReadStatusDeleter chatReadStatusDeleter;

    
    //채팅방 신규 생성, 존재시 해당 ChatRoomID 반환
    public long createdChatRoom(ChatRoomInfo chatRoomInfo) {
        Optional<ChatRoomEntity> chatRoomEntity = chatRoomReader.selectChatRoom(chatRoomInfo);

        if (chatRoomEntity.isPresent()) {
            return chatRoomEntity.get().getId();
        }

        long chatRoomId = chatRoomAppend.append(chatRoomInfo);

        chatDocsAppend.init(chatRoomInfo,chatRoomId);
        return chatRoomId;
    }

    public List<ChatRoomMetaDataInfo> selectChatRoomList(int pageSize, int startPage, String userEmail) {

        List<UserToChatRoomEntity> chatRoomNumber = chatRoomReader.findChatRoomNumber(userEmail);
        List<String> roomNumberList = chatRoomNumber.stream()
                .map(chatRoomEntity -> String.valueOf(chatRoomEntity.getChatRoom().getId()))
                .distinct()
                .toList();

        Map<String, Integer> unreadCountList = updateMessageStatus(roomNumberList, userEmail, pageSize, startPage);
        Map<String, IChatUserInfo> userList = getUserList(chatRoomNumber, userEmail);

        return chatRoomMetaDataReader.findChatRoomMetaDataInfo(roomNumberList, unreadCountList, userList);
    }

    private Map<String, IChatUserInfo> getUserList(List<UserToChatRoomEntity> chatRoomNumber, String userEmail) {
        Map<String, IChatUserInfo> userList = new HashMap<>();

        for (UserToChatRoomEntity userToChatRoomEntity : chatRoomNumber) {
            String chatRoomId = userToChatRoomEntity.getChatRoom().getId().toString();

            // 현재 사용자가 아닌 다른 사용자를 찾기 위한 스트림 사용
            userToChatRoomEntity.getChatRoom().getParticipants().stream()
                    .filter(participant -> !participant.getUser().getAccountId().equals(userEmail))
                    .findFirst() // 첫 번째로 조건을 만족하는 사용자 찾기
                    .ifPresent(participant -> {
                        IChatUserInfo userInfo = IChatUserInfo.of(participant.getUser());
                        userList.put(chatRoomId, userInfo);
                    });
        }
        return userList;
    }

    private Map<String, Integer> updateMessageStatus(List<String> chatRoomNumber,String userEmail, int pageSize, int startPage) {
        Map<String, Integer> unreadCountList = new HashMap<>();
        for (String roomId : chatRoomNumber) {
            String chatRoomId = String.valueOf(roomId);
            ChatReadStatusDocs chatReadStatusDocs = chatReadStatusReader.selectChatMessageLastStatus(chatRoomId, userEmail);
            LocalDateTime lastReadTimestamp = chatReadStatusDocs.checkLastReadTimestamp();
            Integer unreadCount = chatMessageReader.countChatMessagesHistoryToLastDataAndUserName(chatRoomId,userEmail,lastReadTimestamp, startPage, pageSize);
            unreadCountList.put(chatRoomId, unreadCount);
        }
        return unreadCountList;
    }

    public void deletRoom(String roomId) {
        List<String> strings = chatRoomReader.selectChatRoomUserList(roomId);
        if (strings.isEmpty()) {
            throw new NullPointerException("해당 채팅방이 존재하지 않습니다.");
        }
        chatRoomDeleter.deleteChatRoom(roomId);
        chatMessageDeleter.deleteChatMessageDocs(roomId);
        chatRoomMetaDataDeleter.deleteChatRoomMetaDataDocs(roomId);
        chatReadStatusDeleter.deleteChatReadStatusDocs(strings, roomId);
    }

    public String selectChatRoomUserEmail(String chatRoomId, String userEmail) {
        List<String> userEmailList = chatRoomReader.selectChatRoomUserList(chatRoomId);
        if (userEmailList.isEmpty()) {
            throw new NullPointerException("해당 채팅방이 존재하지 않습니다.");
        }
        return userEmail.equals(userEmailList.get(0)) ? userEmailList.get(1) : userEmailList.get(0);
    }
}
