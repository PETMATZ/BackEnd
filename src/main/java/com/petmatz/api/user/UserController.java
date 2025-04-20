package com.petmatz.api.user;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.dto.request.*;
import com.petmatz.api.user.dto.response.*;
import com.petmatz.application.page.dto.MyPageInfo;
import com.petmatz.application.page.dto.OtherProfileInfo;
import com.petmatz.application.page.port.MyPageUseCasePort;
import com.petmatz.application.user.port.UserUseCasePort;
import com.petmatz.common.security.jwt.JwtExtractProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    //주요 비즈니스 로직
    private final UserUseCasePort userUseCasePort;
    private final MyPageUseCasePort myPageUseCasePort;
    private final JwtExtractProvider jwtExtractProvider;



    @PostMapping("/update-location")
    public Response<Void> updateLocation(@RequestBody @Valid UpdateLocationRequest requestBody) {
        userUseCasePort.updateLocation(requestBody.getLatitude(), requestBody.getLongitude());
        return Response.success();
    }

    @GetMapping("/get-myprofile")
    public Response<MyProfileResponse> getMypage() {
        Long myId = jwtExtractProvider.findIdFromJwt();
        MyPageInfo myPage = myPageUseCasePort.getMyPage(myId);
        return Response.success(new MyProfileResponse(myPage));
    }

    @GetMapping("/get-otherprofile")
    public Response<OtherProfileResponse> getOtherMypage(@RequestParam @Valid Long userId) {
        Long myId = jwtExtractProvider.findIdFromJwt();
        OtherProfileInfo otherMypage = myPageUseCasePort.getOtherMypage(userId, myId);
        return Response.success(OtherProfileResponse.to(otherMypage));
    }

    @PostMapping("/edit-myprofile")
    public Response<Void> editMyProfile(@RequestBody @Valid EditMyProfileRequest requestBody) {
        Long userId = jwtExtractProvider.findIdFromJwt();
        String userEmail = jwtExtractProvider.findAccountIdFromJwt();
        String imgURL = myPageUseCasePort.editMyProfile(EditMyProfileRequest.to(requestBody), userId, userEmail);
        return Response.success(imgURL);
    }

}
