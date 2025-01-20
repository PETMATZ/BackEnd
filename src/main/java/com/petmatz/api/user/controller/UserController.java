package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.DeleteIdRequestDto;
import com.petmatz.api.user.request.EditKakaoProfileRequestDto;
import com.petmatz.domain.user.service.KakaoUserService;
import com.petmatz.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @DeleteMapping("/delete-user")
    public Response<Void> deleteUser(@RequestBody @Valid DeleteIdRequestDto requestBody) {
        userService.deleteId(requestBody);
        return Response.success();
    }

    @PostMapping("/edit-kakaoprofile")
    public Response<Void> editKakaoProfile(@RequestBody @Valid EditKakaoProfileRequestDto requestBody) {
        kakaoUserService.editKakaoProfile(EditKakaoProfileRequestDto.of(requestBody));
        return Response.success();
    }
}
