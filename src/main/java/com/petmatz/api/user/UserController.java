package com.petmatz.api.user;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.dto.*;
import com.petmatz.api.utils.CookieComponent;
import com.petmatz.application.email.port.EmailUseCasePort;
import com.petmatz.application.heart.dto.HeartedUserInfo;
import com.petmatz.application.heart.port.HeartUseCasePort;
import com.petmatz.application.page.dto.MyPageInfo;
import com.petmatz.application.page.port.MyPageUseCasePort;
import com.petmatz.application.user.dto.SignInInfo;
import com.petmatz.application.user.port.JwtProviderPort;
import com.petmatz.application.user.port.UserUseCasePort;
import com.petmatz.common.security.jwt.JwtExtractProvider;
import garbege.api.user.request1.HeartingRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    //주요 비즈니스 로직
    private final UserUseCasePort userUseCasePort;
    private final EmailUseCasePort emailUseCasePort;
    private final HeartUseCasePort heartUseCasePort;
    private final MyPageUseCasePort myPageUseCasePort;
    //Cookie Setting
    private final CookieComponent cookieComponent;
    //Token 관련
    private final JwtProviderPort jwtProviderPort;
    private final JwtExtractProvider jwtExtractProvider;

    @PostMapping("/sign-up")
    public Response<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest requestBody){
        SignUpResponse responseDto = userUseCasePort.signUp(SignUpRequest.of(requestBody));
        return Response.success(responseDto);
    }

    @Operation(summary = "로그인", description = "로그인 API")
    @Parameters({
            @Parameter(name = "accountId", description = "Email", example = "test@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "password1212@"),
    })
    @PostMapping("/sign-in")
    public Response<SignInResponse> signIn(@RequestBody @Valid SignInRequest requestBody, HttpServletResponse response){
        SignInInfo signInInfo = userUseCasePort.signIn(requestBody.getAccountId(), requestBody.getPassword());
        cookieComponent.setAccessTokenCookie(response, signInInfo.getAccessCookie());
        cookieComponent.setAccessTokenCookie(response, signInInfo.getRefreshCookie());
        return Response.success(SignInResponse.to(signInInfo));
    }

    @PostMapping("/logout")
    public Response<Void> logout(HttpServletResponse response) {
        cookieComponent.logOut(response);
        return Response.success();
    }

    @PostMapping("/email-certification")
    public Response<Void> emailCertification(@RequestBody @Valid EmailCertificationRequest requestBody) {
        emailUseCasePort.emailCertification(requestBody.getAccountId());
        return Response.success();
    }

    @PostMapping("/check-certification")
    public Response<Void> checkCertification(@RequestBody @Valid CheckCertificationRequest requestBody){
        emailUseCasePort.checkCertification(requestBody.getAccountId(), requestBody.getCertificationNumber());
        return Response.success();
    }

    @PostMapping("/token/reissue")
    public Response<Void> reissueAccessToken(HttpServletResponse response, String refreshToken) {
        String refreshAccessToken = jwtProviderPort.refreshAccessToken(refreshToken);
        cookieComponent.setAccessTokenCookie(response, refreshAccessToken);
        return Response.success();
    }

    @PostMapping("/update-location")
    public Response<Void> updateLocation(@RequestBody @Valid UpdateLocationRequest requestBody) {
        userUseCasePort.updateLocation(requestBody.getLatitude(), requestBody.getLongitude());
        return Response.success();
    }

    @PostMapping("/hearting")
    public Response<Void> hearting(@RequestBody @Valid HeartingRequestDto requestBody) {
        Long myId = jwtExtractProvider.findIdFromJwt();
        heartUseCasePort.toggleHeart(myId, requestBody.getHeartedId());
        return Response.success();
    }

    @GetMapping("/get-heartlist")
    public Response<List<HeartedListResponse>> getHeartedList() {
        Long myId = jwtExtractProvider.findIdFromJwt();
        List<HeartedUserInfo> heartList = heartUseCasePort.getHeartList(myId);
        return Response.success(heartList.stream().map(HeartedListResponse::to).toList());
    }

    @GetMapping("/get-myprofile")
    public Response<MyProfileResponse> getMypage() {
        Long myId = jwtExtractProvider.findIdFromJwt();
        MyPageInfo myPage = myPageUseCasePort.getMyPage(myId);
        return Response.success(new MyProfileResponse(myPage));
    }

//    @GetMapping("/get-otherprofile")
//    public Response<GetOtherProfileResponseDto> getOtherMypage(@RequestParam @Valid Long userId) {
//        GetOtherProfileResponseDto otherMypage = pageService.getOtherMypage(userId);
//        return Response.success(otherMypage);
//    }
//
//    @PostMapping("/edit-myprofile")
//    public Response<EditMyProfileResponseDto> editMyProfile(@RequestBody @Valid EditMyProfileRequestDto requestBody) throws MalformedURLException {
//        EditMyProfileResponseDto editMyProfileResponseDto = pageService.editMyProfile(EditMyProfileRequestDto.of(requestBody));
//        return Response.success(editMyProfileResponseDto);
//    }

}
