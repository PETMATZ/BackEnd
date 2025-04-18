package garbege.api.user.controller;

import com.petmatz.api.global.dto.Response;
import garbege.api.user.request1.*;
import garbege.service.user.response.EditMyProfileResponseDto;
import com.petmatz.api.user.dto.MyProfileResponse;
import garbege.service.user.response.GetOtherProfileResponseDto;
import garbege.service.user.service.HeartService;
import garbege.service.user.service.KakaoUserService;
import garbege.service.user.service.PageService;
import garbege.service.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    private final PageService pageService;
    private final HeartService heartService;


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

//    @PostMapping("/hearting")
//    public Response<Void> hearting(@RequestBody @Valid HeartingRequestDto requestBody) {
//        heartService.hearting(requestBody);
//        return Response.success();
//    }
//
//    @GetMapping("/get-heartlist") // 이거 테스트 필요
//    public Response<List<HeartedUserDto>> getHeartedList() {
//        List<HeartedUserDto> heartedList = heartService.getHeartedList();
//        return Response.success(heartedList);
//    }

//    @GetMapping("/get-myprofile")
//    public Response<MyProfileResponse> getMypage() {
//        MyProfileResponse myPage = pageService.getMypage();
//        return Response.success(myPage);
//    }

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
