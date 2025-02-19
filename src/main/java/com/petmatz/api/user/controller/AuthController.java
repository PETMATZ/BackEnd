package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.CheckCertificationRequestDto;
import com.petmatz.api.user.request.DeleteIdRequestDto;
import com.petmatz.api.user.request.SignInRequestDto;
import com.petmatz.api.user.request.SignUpRequestDto;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.response.CheckCertificationResponseDto;
import com.petmatz.domain.user.response.DeleteIdResponseDto;
import com.petmatz.domain.user.response.SignInResponseDto;
import com.petmatz.domain.user.response.SignUpResponseDto;
import com.petmatz.domain.user.service.AuthService;
import com.petmatz.user.common.LogInResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.net.MalformedURLException;
import java.security.cert.CertificateException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public Response<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody, HttpServletResponse response) throws AuthenticationException {
        SignInResponseDto result = authService.signIn(SignInRequestDto.of(requestBody), response);
        return Response.success(result);
    }

    @PostMapping("/logout")
    public Response<Void> logout(HttpServletResponse res) {
        authService.logout(res);
        return Response.success();
    }

    @PostMapping("/sign-up")
    public Response<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) throws MalformedURLException {
        SignUpResponseDto responseDto = authService.signUp(SignUpRequestDto.of(requestBody));
        return Response.success(responseDto);
    }

    @PostMapping("/check-certification")
    public Response<Void> checkCertification(@RequestBody @Valid CheckCertificationRequestDto requestBody) throws CertificateException {
        authService.checkCertification(CheckCertificationRequestDto.of(requestBody));
        return Response.success();
    }
}
