//package com.petmatz.api.petmission;
//
//import com.petmatz.api.global.dto.Response;
//import com.petmatz.api.petmission.dto.*;
//import com.petmatz.api.utils.SendChatMessage;
//import com.petmatz.application.user.port.UserUseCasePort;
//import com.petmatz.common.constants.PetMissionStatusZip;
//import com.petmatz.infra.jwt.JwtExtractProvider;
//import com.petmatz.domain.old.chatting.ChatMessageService;
//import com.petmatz.application.chat.dto.ChatMessageInfo;
//import com.petmatz.domain.old.petmission.PetMissionService;
//import com.petmatz.domain.old.petmission.dto.PetMissionAnswerInfo;
//import com.petmatz.domain.old.petmission.dto.PetMissionData;
//import com.petmatz.domain.old.petmission.dto.PetMissionDetails;
//import com.petmatz.domain.old.petmission.dto.UserToPetMissionListInfo;
//import com.petmatz.domain.old.petmission.entity.UserToPetMissionEntity;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.MalformedURLException;
//import java.util.List;
//
//
////TODO 펫미션 고민
//@RestController
//@RequestMapping("/api/v1/pet/mission")
//@RequiredArgsConstructor
//public class PetMissionController {
//
//    private final PetMissionService petMissionService;
//    private final ChatMessageService chatService;
//    private final UserUseCasePort userUseCasePort;
//    private final SendChatMessage sendChatMessage;
//
//    private final JwtExtractProvider jwtExtractProvider;
//
//    //TODO 펫 미션 제외, 삭제 예정
//    @Operation(summary = "멍멍이의 부탁 등록", description = "멍멍이의 부탁을 등록하는 API")
//    @Parameters({
//            @Parameter(name = "receiverId", description = "돌봄이 ID", example = "2"), //-> 레포에서 조회 해서 가져와햐 함.
//            @Parameter(name = "petId", description = "펫 ID [ List<String> ] ", example = "[1,2,3]"),
//            @Parameter(name = "missionStarted", description = "멍멍이의 부탁 시작일", example = "히힛"),
//            @Parameter(name = "missionEnd", description = "멍멍이의 부탁 마감", example = "하핫"),
//            @Parameter(name = "petMissionAsk", description = "멍멍이의 부탁 [ List<String> ]", example = "[호수공원 산책하기, 놀아주기]")
//    })
//    @PostMapping
//    public Response<PetMissionResponse> savePetMissionList(@RequestBody PetMissionRequest petMissionRequest) {
//        Long careId = jwtExtractProvider.findIdFromJwt();
//        String careEmail = jwtExtractProvider.findAccountIdFromJwt();
//        String receiverEmail = userUseCasePort.selectUserAccountId(petMissionRequest.receiverId());
//        PetMissionData petMissionData = petMissionService.insertPetMission(petMissionRequest.of(), careId);
//
//        chatService.updateMessage(petMissionRequest.ofto(), petMissionData, receiverEmail);
//
//        //채팅 메세지에 UUID 담아서 보내기
//        ChatMessageInfo chatMessageInfo = petMissionRequest.of(petMissionData.petMissionId(), careEmail,receiverEmail);
//
//        PetMissionResponse petMissionResponse = PetMissionResponse.of(petMissionData);
//        sendChatMessage.sendChatMessage(petMissionData.chatRoomId(), chatMessageInfo);
//        return Response.success(petMissionResponse);
//    }
//
//    @Operation(summary = "멍멍이의 부탁 리스트 조회", description = "멍멍이 리스트 조회 API")
//    @GetMapping
//    public Response<List<UserToPetMissionListInfo>> selectPetMissionList() {
//        Long userId = jwtExtractProvider.findIdFromJwt();
//
//        List<UserToPetMissionEntity> userToPetMissionEntities = petMissionService.selectPetMissionList(userId);
//
//        List<UserToPetMissionListInfo> list = userToPetMissionEntities.stream().map(
//                userToPetMissionEntity ->UserToPetMissionListInfo.of(userToPetMissionEntity, petMissionService.selectUserToPetMissionList(String.valueOf(userToPetMissionEntity.getPetMission().getId())))
//        ).toList();
//        return Response.success(list);
//    }
//
//    @Operation(summary = "멍멍이의 부탁 상태 업데이트", description = "멍멍이의 부탁 상태를 업데이트 하는 API")
//    @Parameters({
//            @Parameter(name = "careEmail", description = "맡김이 Email", example = "2"),
//            @Parameter(name = "receiverEmail", description = "돌봄이 Email", example = "2"),
//            @Parameter(name = "petMissionId", description = "펫 미션 ID", example = "2"),
//            @Parameter(name = "missionStatusZip", description = "미션 상태 [ PLG, END ]", example = "PLG"),
//    })
//    @PutMapping
//    public Response<Void> updatePetMissionStatus(@RequestBody PetMissionUpdateRequest petMissionUpdateRequest) {
//        petMissionService.updatePetMissionStatus(petMissionUpdateRequest);
//        if (PetMissionStatusZip.AFT.equals(petMissionUpdateRequest.missionStatusZip())) {
//            PetMissionDetails petMissionDetails = petMissionService.selectPetMissionInfo(petMissionUpdateRequest.petMissionId());
//            String chatRoomId = petMissionService.selectChatRoomId(petMissionDetails.careEmail(), petMissionDetails.receiverEmail());
//            ChatMessageInfo chatMessageInfo = petMissionUpdateRequest.of(petMissionUpdateRequest.petMissionId());
//            chatService.updateMessage(petMissionUpdateRequest.of(petMissionUpdateRequest.petMissionId()), chatRoomId);
//            //채팅 메세지에 UUID 담아서 보내기
//            sendChatMessage.sendChatMessage(chatRoomId, chatMessageInfo);
//            return Response.success("업데이트가 정상적으로 되었습니다.");
//        }
//        return Response.success("업데이트가 정상적으로 되었습니다.");
//    }
//
//
//    @Operation(summary = "펫 미션 상세 조회", description = "펫 미션 상세 조회 API")
//    @Parameters({
//            @Parameter(name = "petMissionId", description = "펫 미션 ID", example = "2"),
//    })
//    @GetMapping("/Info")
//    public Response<PetMissionDetails> selectPetMissionInfo(@RequestParam("petMissionId") String petMissionId) {
//        PetMissionDetails petMissionDetails = petMissionService.selectPetMissionInfo(petMissionId);
//        return Response.success(petMissionDetails);
//    }
//
//    @Operation(summary = "펫 미션 답글 달기", description = "펫 미션 답글 API")
//    @Parameters({
//            @Parameter(name = "askId", description = "펫 미션 ID [ 작은 틀 ]", example = "2"),
//            @Parameter(name = "comment", description = "답글 내용", example = "2"),
//            @Parameter(name = "imgURL", description = "사진 없으면 빈 값, 있으면 Y", example = "2"),
//    })
//    @PostMapping("/comment")
//    public String saveComment(@RequestBody PetMissionCommentRequest petMissionCommentRequest) throws MalformedURLException {
//        String userEmail = jwtExtractProvider.findAccountIdFromJwt();
//        return petMissionService.updatePetMissionComment(petMissionCommentRequest.of(), userEmail);
//    }
//
//
//    //TODO Ask 상세 조회
//    @GetMapping("/comment/answer/info")
//    @Operation(summary = "답변 상세 조회", description = "등록된 하나의 답변을 상세 조회 API")
//    @Parameter(name = "answerId", description = "질문 ID", example = "1")
//    public PetMissionCommentResponse getCommentInfo(@RequestParam("answerId") String answerId) {
//        PetMissionAnswerInfo petMissionAnswerInfo = petMissionService.selectPetMissionAnswerInfo(answerId);
//        return petMissionAnswerInfo.of();
//    }
//
///*
//    @DeleteMapping
//    public void deletePetMissionList() {
//
//    }
//*/
//
//}
