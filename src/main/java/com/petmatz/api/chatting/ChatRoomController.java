//package com.petmatz.api.chatting;
//
//import com.petmatz.api.chatting.dto.ChatRoomMetaDataInfoResponse;
//import com.petmatz.api.chatting.dto.MatchRequest;
//import com.petmatz.api.global.dto.Response;
//import com.petmatz.application.chat.port.ChatRoomUserCasePort;
//import com.petmatz.application.jwt.port.JwtUserPort;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/match")
//public class ChatRoomController {
//
//    private final ChatRoomUserCasePort chatRoomUserCasePort;
//    private final JwtUserPort jwtUserPort;
//
//    @PostMapping
//    @Operation(summary = "채팅방 생성", description = "채팅방을 생성하는 API API")
//    @Parameters({
//            @Parameter(name = "caregiverId", description = "반려인 userId", example = "반려인 ID"),
//            @Parameter(name = "entrustedId", description = "돌봄이 userId", example = "돌봄이 ID")
//    })
//    public Response<Long> matchUsers(@RequestBody MatchRequest matchRequest) {
//        long chatRoomNumber = chatRoomUserCasePort.createdChatRoom(matchRequest.of());
//        return Response.success(chatRoomNumber);
//    }
//
//
//    @GetMapping
//    @Operation(summary = "채팅방 조회", description = "해당 사용자가 보유하고 있는 채팅방 조회 API")
//    @Parameters({
//            @Parameter(name = "pageSize", description = "default : 5", example = "5"),
//            @Parameter(name = "startPage", description = "default : 1", example = "1"),
//    })
//    public Response<List<ChatRoomMetaDataInfoResponse>> chatRoomsList(
//            @RequestParam(defaultValue = "5") int pageSize,
//            @RequestParam(defaultValue = "1") int startPage
//    ) {
//        Long userId = jwtUserPort.findIdFromJwt();
//        List<ChatRoomMetaDataInfoResponse> chatRoomMetaDataInfoResponseList = chatRoomUserCasePort.selectChatRoomList(pageSize, startPage, userId).stream()
//                .map(ChatRoomMetaDataInfoResponse::of)
//                .collect(Collectors.toList());
//
//        return Response.success(chatRoomMetaDataInfoResponseList);
//    }
//
////    @DeleteMapping
////    @Operation(summary = "채팅방 삭제", description = "해당 사용자가 지정한 채팅방을 삭제 API")
////    @Parameters({
////            @Parameter(name = "roomId", description = "삭제하려는 채팅방 고유 NO", example = "1"),
////    })
////    public Response<Void> deleteChatRoom(@RequestParam String roomId) {
////        chatRoomUserCasePort.deleteRoom(roomId);
////        return Response.success("성공적으로 삭제 되었습니다.");
////    }
//
//}
