package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.common.security.jwt.JwtManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JwtController {

    private final JwtManager jwtManager;

    @PostMapping("/token/reissue")
    public Response<Void> reissueAccessToken(HttpServletResponse response, String refreshToken) {
        jwtManager.refreshAccessToken(response, refreshToken);
        return Response.success();
    }
}
