package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.RepasswordRequestDto;
import com.petmatz.api.user.request.SendRepasswordRequestDto;
import com.petmatz.domain.user.component.PasswordService;
import com.petmatz.domain.user.response.RepasswordResponseDto;
import com.petmatz.domain.user.response.SendRepasswordResponseDto;
import com.petmatz.domain.user.service.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/send-repassword")
    public Response<SendRepasswordResponseDto> sendRepassword(@RequestBody @Valid SendRepasswordRequestDto requestBody) {
        SendRepasswordResponseDto sendRepasswordResponseDto = passwordService.sendRepassword(requestBody);
        return Response.success(sendRepasswordResponseDto);
    }

    @PostMapping("/repassword")
    public Response<Void> repassword(@RequestBody @Valid RepasswordRequestDto requestBody) {
        passwordService.repassword(RepasswordRequestDto.of(requestBody));
        return Response.success();
    }
}
