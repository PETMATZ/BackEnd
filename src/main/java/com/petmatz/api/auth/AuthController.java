package com.petmatz.api.auth;

import com.petmatz.api.auth.dto.*;
import com.petmatz.api.global.dto.Response;
import com.petmatz.api.utils.CookieComponent;
import com.petmatz.application.user.dto.SignInInfo;
import com.petmatz.application.jwt.port.JwtProviderPort;
import com.petmatz.application.user.port.UserUseCasePort;
import com.petmatz.infra.jwt.JwtExtractProvider;
import com.petmatz.api.auth.dto.RepasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserUseCasePort userUseCasePort;
    //Cookie Setting
    private final CookieComponent cookieComponent;
    //Token 관련
    private final JwtProviderPort jwtProviderPort;
    private final JwtExtractProvider jwtExtractProvider;


    @Operation(
            summary = "회원가입 API",
            description = "입력한 정보를 토대로 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/sign-up")
    public Response<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest requestBody){
        SignUpResponse responseDto = userUseCasePort.signUp(SignUpRequest.of(requestBody));
        return Response.success(responseDto);
    }


    @Operation(
            summary = "로그인 API",
            description = "아이디와 비밀번호를 통해 로그인을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/sign-in")
    public Response<SignInResponse> signIn(@RequestBody @Valid SignInRequest requestBody, HttpServletResponse response){
        SignInInfo signInInfo = userUseCasePort.signIn(requestBody.getAccountId(), requestBody.getPassword());
        cookieComponent.setAccessTokenCookie(response, signInInfo.getAccessCookie());
        cookieComponent.setAccessTokenCookie(response, signInInfo.getRefreshCookie());
        return Response.success(SignInResponse.to(signInInfo));
    }

    @Operation(
            summary = "회원 탈퇴 API",
            description = "비밀번호 검증을 통해 회원탈퇴를 진행합니다. ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/secession")
    public Response<Void> Secession(@RequestBody @Valid UserDeleteRequest request) {
        Long userId = jwtExtractProvider.findIdFromJwt();
        userUseCasePort.secession(userId, request.getPassword());
        return Response.success();
    }

    @Operation(
            summary = "로그아웃 API",
            description = "토큰을 삭제하며, 로그아웃을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/logout")
    public Response<Void> logout(HttpServletResponse response) {
        cookieComponent.logOut(response);
        return Response.success();
    }

    @Operation(
            summary = "Access Token 재발급 API",
            description = "Refresh Token을 이용해 AccessToken을 재발급 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/token/reissue")
    public Response<Void> reissueAccessToken(HttpServletResponse response, @Schema(description = "Refresh Token") String refreshToken) {
        String refreshAccessToken = jwtProviderPort.refreshAccessToken(refreshToken);
        cookieComponent.setAccessTokenCookie(response, refreshAccessToken);
        return Response.success();
    }

    @Operation(
            summary = "비밀번호 재설정 API",
            description = "비밀번호 검증을 통해, 비밀번호를 재설정 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/repassword")
    public Response<Void> repassword(@RequestBody @Valid RepasswordRequest requestBody) {
        Long userId = jwtExtractProvider.findIdFromJwt();
        userUseCasePort.updatePassword(requestBody.getCurrentPassword(), requestBody.getNewPassword(), userId);
        return Response.success();
    }

}
