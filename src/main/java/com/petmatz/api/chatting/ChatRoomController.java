package com.petmatz.api.chatting;

import com.petmatz.api.chatting.dto.ChatRoomMetaDataInfoResponse;
import com.petmatz.api.chatting.dto.MatchRequest;
import com.petmatz.api.global.dto.Response;
import com.petmatz.domain.old.chatting.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/match")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtExtractProvider jwtExtractProvider;

    @PostMapping
    @Operation(summary = "채팅방 생성", description = "채팅방을 생성하는 API API")
    @Parameters({
            @Parameter(name = "caregiverEmail", description = "반려인 닉네임", example = "반려인이름"),
            @Parameter(name = "entrustedEmail", description = "돌봄이 닉네임", example = "돌봄이이름")
    })
    public Response<Long> matchUsers(@RequestBody MatchRequest matchRequest) {
        long chatRoomNumber = chatRoomService.createdChatRoom(matchRequest.of());
        return Response.success(chatRoomNumber);
    }


    @GetMapping
    @Operation(summary = "채팅방 조회", description = "해당 사용자가 보유하고 있는 채팅방 조회 API")
    @Parameters({
            @Parameter(name = "pageSize", description = "default : 5", example = "5"),
            @Parameter(name = "startPage", description = "default : 1", example = "1"),
    })
    public Response<List<ChatRoomMetaDataInfoResponse>> chatRoomsList(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "1") int startPage
    ) {
        String userEmail = jwtExtractProvider.findAccountIdFromJwt();

        List<ChatRoomMetaDataInfoResponse> chatRoomMetaDataInfoResponseList = chatRoomService.selectChatRoomList(pageSize, startPage, userEmail).stream()
                .map(ChatRoomMetaDataInfoResponse::of)
                .collect(Collectors.toList());

        return Response.success(chatRoomMetaDataInfoResponseList);
    }

    @DeleteMapping
    @Operation(summary = "채팅방 삭제", description = "해당 사용자가 지정한 채팅방을 삭제 API")
    @Parameters({
            @Parameter(name = "roomId", description = "삭제하려는 채팅방 고유 NO", example = "1"),
    })
    public Response<Void> deleteChatRoom(@RequestParam String roomId) {
        chatRoomService.deleteRoom(roomId);
        return Response.success("성공적으로 삭제 되었습니다.");
    }

}
