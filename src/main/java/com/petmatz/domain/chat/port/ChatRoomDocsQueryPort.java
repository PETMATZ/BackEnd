package com.petmatz.domain.chat.port;

import com.petmatz.application.chat.dto.ChatRoomMetaDataInfo;
import com.petmatz.application.chat.dto.IChatUserInfo;
import com.petmatz.domain.chat.ChatRoom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ChatRoomDocsQueryPort {

    List<ChatRoomMetaDataInfo> findChatRoomMetaDataInfo(List<ChatRoom> chatRoomNumbers, Map<String, Integer> unreadCountList, Map<String, IChatUserInfo> userList);

    Integer countChatMessagesHistoryToLastDataAndUserName(String chatRoomsId, Long userId, LocalDateTime lastReadTimestamp, int pageNumber, int pageSize);
}
