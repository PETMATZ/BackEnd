package com.petmatz.domain.chatting;

import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.chatting.component.ChatMessageReader;
import com.petmatz.domain.chatting.component.ChatMessageUpdater;
import com.petmatz.domain.chatting.dto.ChatMessageInfo;

import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.chatting.docs.ChatReadStatusDocs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final UserRepository userRepository;

    private final ChatMessageReader chatMessageReader;
    private final ChatMessageUpdater chatMessageUpdater;
    private final JwtExtractProvider jwtExtractProvider;


    public Page<ChatMessageInfo> selectMessage(String receiver, String chatRoomsId, int pageNumber, int pageSize) {
        String sender = jwtExtractProvider.findAccountIdFromJwt();

        // 페이징된 메시지 가져오기
        List<ChatMessageInfo> chatMessageInfos = chatMessageReader.selectChatMessagesHistory(chatRoomsId, pageNumber, pageSize);

        // 반환 데이터에 읽음 상태 업데이트
        ChatReadStatusDocs chatReadStatusDocs = chatMessageReader.selectChatMessageLastStatus(chatRoomsId, receiver);
        LocalDateTime lastReadTimestamp = chatReadStatusDocs.checkLastReadTimestamp();
        List<ChatMessageInfo> updatedMessages = messageStatusUpdate(chatMessageInfos, lastReadTimestamp);

        //상대편 정보 조회
        User byAccountId = userRepository.findByAccountId(receiver);

        // 전체 메시지 개수 가져오기
        long totalElements = chatMessageReader.countChatMessages(chatRoomsId);

        return new PageImpl<>(updatedMessages, PageRequest.of(pageNumber - 1, pageSize), totalElements);
    }

    private List<ChatMessageInfo> messageStatusUpdate(List<ChatMessageInfo> chatMessageInfos, LocalDateTime lastReadTimestamp) {
        if (lastReadTimestamp == null) return chatMessageInfos;
        return chatMessageInfos.stream()
                .peek(message -> {
                    message.changeReadStatus(lastReadTimestamp);
                })
                .collect(Collectors.toList());
    }

    public void updateMessage(ChatMessageInfo chatMessageInfo, String chatRoomId) {
        chatMessageUpdater.updateMessage(chatMessageInfo,chatRoomId);
    }

}