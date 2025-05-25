package com.petmatz.application.chat;

import com.petmatz.application.chat.dto.ChatRoomInfo;
import com.petmatz.application.chat.dto.ChatRoomMetaDataInfo;
import com.petmatz.application.chat.dto.IChatUserInfo;
import com.petmatz.application.chat.port.ChatRoomUserCasePort;
import com.petmatz.domain.chat.ChatReadStatus;
import com.petmatz.domain.chat.ChatRoom;
import com.petmatz.domain.chat.port.ChatDocsInitPort;
import com.petmatz.domain.chat.port.ChatReadStatusQueryPort;
import com.petmatz.domain.chat.port.ChatRoomCommandPort;
import com.petmatz.domain.chat.port.ChatRoomQueryPort;
import com.petmatz.persistence.caht.adapter.mongo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomUseCase implements ChatRoomUserCasePort {

    private final ChatDocsInitPort chatDocsInitPort;

    private final ChatRoomQueryPort chatRoomQueryPort;
    private final ChatRoomCommandPort commandPort;
    private final ChatRoomDocsQueryAdapter chatRoomDocsQueryAdapter;
    private final ChatReadStatusQueryPort chatReadStatusQueryPort;

    /**
     * 채팅방을 새로 생성한다, 단 채팅방이 이미 존재하면 해당 ID를 반환한다.
     * @param chatRoomInfo 돌봄이, 맡김이 Email
     * @return 생성된 채팅방 수를 반환한다. : long
     */
    @Override
    public long createdChatRoom(ChatRoomInfo chatRoomInfo) {
        chatRoomQueryPort.selectChatRoom(chatRoomInfo);
        long chatRoomId = commandPort.save(chatRoomInfo);
        chatDocsInitPort.init(chatRoomInfo,chatRoomId);
        return chatRoomId;
    }

    /**
     * 채팅방 리스트를 가져온다. [ 본인이 포함되어 있는거 전부 ]
     * @param pageSize 한 페이지당 가져올 채팅방 개수
     * @param startPage 시작 페이지 번호
     * @param userId 본인 고유의 userID
     * @return ChatRoomMetaDataInfo 형태의 채팅방 리스트
     */
    @Override
    public List<ChatRoomMetaDataInfo> selectChatRoomList(int pageSize, int startPage, Long userId) {

        List<ChatRoom> chatRoomNumber = chatRoomQueryPort.findChatRoomNumber(userId);

        Map<String, Integer> unreadCountList = updateMessageStatus(chatRoomNumber, userId, pageSize, startPage);
        Map<String, IChatUserInfo> userList = getUserList(chatRoomNumber, userId);

        return chatRoomDocsQueryAdapter.findChatRoomMetaDataInfo(chatRoomNumber, unreadCountList, userList);
    }

    /**
     *
     * @param chatRoomNumber 채팅방 고유 번호
     * @return 유저의 값을 반환한다 : Key -> chatRoomId, Value -> IChatUserInfo
     */
    private Map<String, IChatUserInfo> getUserList(List<ChatRoom> chatRoomNumber, Long userId) {
        return chatRoomNumber.stream()
                .collect(Collectors.toMap(
                        chatRoomNumberList -> chatRoomNumberList.getId().toString(), // key: chatRoomId
                        userToChatRoomEntity -> IChatUserInfo.of(userToChatRoomEntity.getUser()) // value: IChatUserInfo
                ));
    }

    private Map<String, Integer> updateMessageStatus(List<ChatRoom> chatRoomNumber,Long userId, int pageSize, int startPage) {
        Map<String, Integer> unreadCountList = new HashMap<>();
        for (ChatRoom chatRoom : chatRoomNumber) {
            String chatRoomId = String.valueOf(chatRoom.getId());
            ChatReadStatus chatReadStatusDocs = chatReadStatusQueryPort.selectChatMessageLastStatus(chatRoomId, userId);
            LocalDateTime lastReadTimestamp = chatReadStatusDocs.checkLastReadTimestamp();
            Integer unreadCount = chatRoomDocsQueryAdapter.countChatMessagesHistoryToLastDataAndUserName(chatRoomId,userId,lastReadTimestamp, startPage, pageSize);
            unreadCountList.put(chatRoomId, unreadCount);
        }
        return unreadCountList;
    }
//
//    public void deleteRoom(String roomId) {
//        List<String> strings = chatRoomReader.selectChatRoomUserList(roomId);
//        if (strings.isEmpty()) {
//            throw new UserException(UserErrorCode.USER_NOT_FOUND);
//        }
//        chatRoomDeleter.deleteChatRoom(roomId);
//        chatMessageDeleter.deleteChatMessageDocs(roomId);
//        chatRoomMetaDataDeleter.deleteChatRoomMetaDataDocs(roomId);
//        chatReadStatusDeleter.deleteChatReadStatusDocs(strings, roomId);
//    }
//
//    public String selectChatRoomUserEmail(String chatRoomId, String userEmail) {
//        List<String> userEmailList = chatRoomReader.selectChatRoomUserList(chatRoomId);
//        if (userEmailList.isEmpty()) {
//            throw new UserException(UserErrorCode.USER_NOT_FOUND);
//        }
//        return userEmail.equals(userEmailList.get(0)) ? userEmailList.get(1) : userEmailList.get(0);
//    }
}
