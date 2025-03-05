package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.EditMyProfileRequestDto;
import com.petmatz.domain.user.response.EditMyProfileResponseDto;
import com.petmatz.domain.user.response.GetMyProfileResponseDto;
import com.petmatz.domain.user.response.GetOtherProfileResponseDto;
import com.petmatz.domain.user.service.PageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class PageController {

    private final PageService pageService;

    @GetMapping("/get-myprofile")
    public Response<GetMyProfileResponseDto> getMypage() {
        GetMyProfileResponseDto myPage = pageService.getMypage();
        return Response.success(myPage);
    }

    @GetMapping("/get-otherprofile")
    public Response<GetOtherProfileResponseDto> getOtherMypage(@RequestParam @Valid Long userId) {
        GetOtherProfileResponseDto otherMypage = pageService.getOtherMypage(userId);
        return Response.success(otherMypage);
    }

    @PostMapping("/edit-myprofile")
    public Response<EditMyProfileResponseDto> editMyProfile(@RequestBody @Valid EditMyProfileRequestDto requestBody) throws MalformedURLException {
        EditMyProfileResponseDto editMyProfileResponseDto = pageService.editMyProfile(EditMyProfileRequestDto.of(requestBody));
        return Response.success(editMyProfileResponseDto);
    }
}
