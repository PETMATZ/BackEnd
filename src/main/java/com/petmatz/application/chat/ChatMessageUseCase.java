//package com.petmatz.application.chat;
//
//import com.petmatz.persistence.caht.mongo.vo.ChatMessageInfo;
//import com.petmatz.application.chat.dto.ChatMessagePetMissionInfo;
//import com.petmatz.application.chat.port.ChatMessageUseCasePort;
//import com.petmatz.persistence.caht.adapter.mongo.ChatMessageReader;
//import com.petmatz.persistence.caht.adapter.mongo.ChatMessageUpdater;
//import com.petmatz.domain.old.petmission.dto.PetMissionData;
//import com.petmatz.persistence.caht.mongo.ChatReadStatusDocs;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ChatMessageUseCase implements ChatMessageUseCasePort {
//
//    private final ChatMessageReader chatMessageReader;
//    private final ChatMessageUpdater chatMessageUpdater;
//
//    /**
//     *
//     * @param receiver 채팅 받는이
//     * @param chatRoomsId 채팅방 고유 번호
//     * @param pageNumber 페이지 번호
//     * @param pageSize 페이지 크기
//     * @param lastFetchTimestamp 마지막 채팅 시간
//     * @return Page<ChatMessageInfo> 형태로 채팅 기록을 반환한다.
//     */
//    public Page<ChatMessageInfo> selectMessage(String receiver, String chatRoomsId, int pageNumber, int pageSize, LocalDateTime lastFetchTimestamp) {
//        // 페이징된 메시지 가져오기
//        List<ChatMessageInfo> chatMessageInfos = chatMessageReader.selectChatMessagesHistory(chatRoomsId, pageNumber, pageSize,lastFetchTimestamp);
//
//        // 반환 데이터에 읽음 상태 업데이트
//        ChatReadStatusDocs chatReadStatusDocs = chatMessageReader.selectChatMessageLastStatus(chatRoomsId, receiver);
//        LocalDateTime lastReadTimestamp = chatReadStatusDocs.checkLastReadTimestamp();
//        List<ChatMessageInfo> updatedMessages = messageStatusUpdate(chatMessageInfos, lastReadTimestamp);
//
//        // 전체 메시지 개수 가져오기
//        long totalElements = chatMessageReader.countChatMessages(chatRoomsId);
//
//        return new PageImpl<>(updatedMessages, PageRequest.of(pageNumber - 1, pageSize), totalElements);
//    }
//
//    private List<ChatMessageInfo> messageStatusUpdate(List<ChatMessageInfo> chatMessageInfos, LocalDateTime lastReadTimestamp) {
//        if (lastReadTimestamp == null) return chatMessageInfos;
//        return chatMessageInfos.stream()
//                .peek(message -> {
//                    message.changeReadStatus(lastReadTimestamp);
//                })
//                .collect(Collectors.toList());
//    }
//
//    public void updateMessage(ChatMessageInfo chatMessageInfo, String chatRoomId) {
//        chatMessageUpdater.updateMessage(chatMessageInfo,chatRoomId);
//    }
//
//    public void updateMessage(ChatMessagePetMissionInfo chatMessageInfo, PetMissionData petMissionData, String receiverEmail) {
//        chatMessageUpdater.updateMessage(chatMessageInfo.of(receiverEmail, petMissionData.petMissionId()),petMissionData.chatRoomId());
//    }
//}
