//package com.petmatz.api.chatting;
//
//import com.petmatz.api.chatting.dto.*;
//import com.petmatz.api.global.dto.Response;
//import com.petmatz.api.utils.SendChatMessage;
//import com.petmatz.application.chat.port.ChatMessageUseCasePort;
//import com.petmatz.application.chat.port.ChatRoomUserCasePort;
//import com.petmatz.application.user.port.UserUseCasePort;
//import com.petmatz.infra.jwt.JwtExtractProvider;
//import com.petmatz.domain.old.chatting.ChatMessageService;
//import com.petmatz.domain.old.chatting.ChatRoomService;
//import com.petmatz.persistence.caht.mongo.vo.ChatMessageInfo;
//import garbege.service.user.info.UserInfo;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import jakarta.annotation.Nullable;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1")
//@Slf4j
//public class ChatController {
//
//    private final UserUseCasePort userUseCasePort;
//    private final ChatMessageUseCasePort chatMessageUseCasePort;
//    private final ChatRoomUserCasePort chatRoomUserCasePort;
//
//    private final JwtExtractProvider jwtExtractProvider;
//    private final SendChatMessage sendChatMessage;
//
//    @MessageMapping("/chat")
//    @Operation(summary = "메세지 전송", description = "양방향 통신 소켓 메세지 전송 API")
//    @Parameters({
//            @Parameter(name = "chatRoomId", description = "채팅방 번호", example = "1"),
//            @Parameter(name = "senderEmail  ", description = "메세지 보내는 이의 Email", example = "test1@naver.com"),
//            @Parameter(name = "receiverEmail  ", description = "메세지 받는 이의 Email", example = "test2@naver.com"),
//            @Parameter(name = "msg", description = "메세지 내용", example = "안녕하세요"),
//            @Parameter(name = "msg_type", description = "메세지 타입", example = "PLG [PLG, MSG, END]")
//    })
//    public void sendPrivateMessage(ChatMessageRequest chatMessageRequest) {
//        ChatMessageInfo chatMessageInfo = chatMessageRequest.of();
//        chatMessageUseCasePort.updateMessage(chatMessageInfo, chatMessageRequest.chatRoomId());
//        sendChatMessage.sendChatMessage(chatMessageRequest.chatRoomId(), chatMessageInfo);
//    }
//
//    @MessageMapping("/chat/{chatRoomId}/read")
//    public void sendReadStatus(@Payload ChatReadStatusDirect chatReadStatusDirect,
//                               @DestinationVariable String chatRoomId) {
//        sendChatMessage.sendChatMessage(chatRoomId, chatReadStatusDirect);
//    }
//
//
//    @GetMapping("/chat/message")
//    @Operation(summary = "메세지 내역 긁어오기", description = "채팅방의 메세지 내역을 긁어오는 API")
//    @Parameters({
//            @Parameter(name = "chatRoomId", description = "채팅방 번호", example = "1"),
//            @Parameter(name = "pageSize", description = "긁어올 페이지의 사이즈", example = "20 ( Default : 15 )"),
//            @Parameter(name = "startPage", description = "현재 페이지의 번호 ( 0은 안됨!! )", example = "3 ( Default 1 )"),
//            @Parameter(name = "lastReadTimestamp", description = "마지막으로 잃은 메시지 타임", example = "2025-02-24T14:30:45.123456")
//    })
//    public Response<ChatMessageResponse> selectChatMessage(
//                                         @RequestParam String chatRoomId,
//                                         @RequestParam(required = false) @Nullable LocalDateTime lastFetchTimestamp,
//                                         @RequestParam(defaultValue = "10") int pageSize,
//                                         @RequestParam(defaultValue = "1") int startPage
//    ) {
//        String userEmail = jwtExtractProvider.findAccountIdFromJwt();
//        String receiverEmail = chatRoomUserCasePort.selectChatRoomUserEmail(chatRoomId, userEmail);
//        Page<ChatMessageInfo> chatMessageInfos = chatMessageUseCasePort.selectMessage(receiverEmail, chatRoomId, startPage, pageSize, lastFetchTimestamp);
//        UserInfo userInfo = userUseCasePort.selectUserInfo(receiverEmail);
//
//        return Response.success(ChatMessageResponse.of(
//                chatMessageInfos.getContent()
//                        .stream().map(ChatMessage::of).toList(),
//                IChatUserResponse.of(userInfo),
//                chatRoomId,
//                chatMessageInfos.getNumber() + 1,
//                chatMessageInfos.getTotalPages(),
//                chatMessageInfos.getTotalElements()));
//    }
//}
