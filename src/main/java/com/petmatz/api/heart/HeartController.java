package com.petmatz.api.heart;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.heart.dto.HeartingRequest;
import com.petmatz.api.heart.dto.HeartedListResponse;
import com.petmatz.application.heart.dto.HeartedUserInfo;
import com.petmatz.application.heart.port.HeartUseCasePort;
import com.petmatz.infra.jwt.JwtExtractProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heart")
public class HeartController {

    private final HeartUseCasePort heartUseCasePort;
    private final JwtExtractProvider jwtExtractProvider;

    @Operation(
            summary = "찜 등록 API",
            description = "사용자를 찜 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping()
    public Response<Void> hearting(@RequestBody @Valid HeartingRequest requestBody) {
        Long myId = jwtExtractProvider.findIdFromJwt();
        heartUseCasePort.toggleHeart(myId, requestBody.getHeartedId());
        return Response.success();
    }

    @Operation(
            summary = "찜 목록 조회 API",
            description = "찜 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @GetMapping()
    public Response<List<HeartedListResponse>> getHeartedList() {
        Long myId = jwtExtractProvider.findIdFromJwt();
        List<HeartedUserInfo> heartList = heartUseCasePort.getHeartList(myId);
        return Response.success(heartList.stream().map(HeartedListResponse::to).toList());
    }
}
