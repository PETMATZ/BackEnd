package com.petmatz.api.user;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.dto.request.*;
import com.petmatz.api.user.dto.response.*;
import com.petmatz.application.main_page.dto.MyPageInfo;
import com.petmatz.application.main_page.dto.OtherProfileInfo;
import com.petmatz.application.main_page.port.MyPageUseCasePort;
import com.petmatz.application.user.port.KaKaoUserUseCasePort;
import com.petmatz.application.user.port.UserUseCasePort;
import com.petmatz.infra.jwt.JwtExtractProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    //주요 비즈니스 로직
    private final UserUseCasePort userUseCasePort;
//    private final KaKaoUserUseCasePort kaKaoUserUseCasePort;
    private final MyPageUseCasePort myPageUseCasePort;
    private final JwtExtractProvider jwtExtractProvider;

    @Operation(
            summary = "위치 업데이트 API",
            description = "사용자의 경도, 위도를 업데이트 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/update-location")
    public Response<Void> updateLocation(@RequestBody @Valid UpdateLocationRequest requestBody) {
        String accountId = jwtExtractProvider.findAccountIdFromJwt();
        userUseCasePort.updateLocation(requestBody.getLatitude(), requestBody.getLongitude(), accountId);
        return Response.success();
    }

    @Operation(
            summary = "마이 페이지 조회 API",
            description = "사용자의 마이페이지를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @GetMapping("/get-myprofile")
    public Response<MyProfileResponse> getMypage() {
        Long myId = jwtExtractProvider.findIdFromJwt();
        MyPageInfo myPage = myPageUseCasePort.getMyPage(myId);
        return Response.success(new MyProfileResponse(myPage));
    }

    @Operation(
            summary = "다른 사용자 페이지 조회 API",
            description = "다른 사용자의 페이지를 조회합니다. ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @GetMapping("/get-otherprofile")
    public Response<OtherProfileResponse> getOtherMypage(@RequestParam @Valid @Schema(defaultValue = "상대방의 User ID", example = "1") Long userId) {
        Long myId = jwtExtractProvider.findIdFromJwt();
        OtherProfileInfo otherMypage = myPageUseCasePort.getOtherMypage(userId, myId);
        return Response.success(OtherProfileResponse.to(otherMypage));
    }

    @Operation(
            summary = "마이페이지 업데이트 API",
            description = "마이 페이지를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/edit-myprofile")
    public Response<Void> editMyProfile(@RequestBody @Valid EditMyProfileRequest requestBody) {
        Long userId = jwtExtractProvider.findIdFromJwt();
        String userEmail = jwtExtractProvider.findAccountIdFromJwt();
        String imgURL = myPageUseCasePort.editMyProfile(EditMyProfileRequest.to(requestBody), userId, userEmail);
        return Response.success(imgURL);
    }

}
