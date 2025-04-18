//package com.petmatz.garbege.api.user.controller;
//
//import com.petmatz.api.global.dto.Response;
//import com.petmatz.api.global.utils.CookieComponent;
//import com.petmatz.api.user.dto.CheckCertificationRequestDto;
//import com.petmatz.api.user.dto.EmailCertificationRequestDto;
//import com.petmatz.api.user.dto.SignInRequestDto;
//import com.petmatz.garbege.api.user.request1.SignUpRequestDto;
//import com.petmatz.application.user.dto.SignInResponseDto;
//import com.petmatz.garbege.service.user.response.SignUpResponse;
//import com.petmatz.garbege.service.user.service.AuthService;
//import com.petmatz.garbege.service.user.service.EmailService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import javax.naming.AuthenticationException;
//import java.net.MalformedURLException;
//import java.security.cert.CertificateException;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService authService;
//    private final EmailService emailService;
//    private final CookieComponent cookieComponent;
//
//
//    @Operation(summary = "로그인", description = "로그인 API")
//    @Parameters({
//            @Parameter(name = "accountId", description = "Email", example = "test@naver.com"),
//            @Parameter(name = "password", description = "비밀번호", example = "password1212@"),
//    })
//    @PostMapping("/sign-in")
//    public Response<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody, HttpServletResponse response) throws AuthenticationException {
//        SignInResponseDto result = authService.signIn(SignInRequestDto.of(requestBody), response);
//        return Response.success(result);
//    }
//
//    @PostMapping("/logout")
//    public Response<Void> logout(HttpServletResponse res) {
//        authService.logout(res);
//        return Response.success();
//    }
//
//    @PostMapping("/sign-up")
//    public Response<SignUpResponse> signUp(@RequestBody @Valid SignUpRequestDto requestBody) throws MalformedURLException {
//        SignUpResponse responseDto = authService.signUp(SignUpRequestDto.of(requestBody));
//        return Response.success(responseDto);
//    }
//
//    @PostMapping("/check-certification")
//    public Response<Void> checkCertification(@RequestBody @Valid CheckCertificationRequestDto requestBody) throws CertificateException {
//        authService.checkCertification(CheckCertificationRequestDto.of(requestBody));
//        return Response.success();
//    }
//
//    @PostMapping("/email-certification")
//    public Response<Void> emailCertification(@RequestBody @Valid EmailCertificationRequestDto requestBody) {
//        emailService.emailCertification(requestBody);
//        return Response.success();
//    }
//}
