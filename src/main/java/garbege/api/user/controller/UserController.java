//package garbege.api.user.controller;
//
//import com.petmatz.api.auth.dto.UserDeleteRequest;
//import com.petmatz.api.global.dto.Response;
//import garbege.api.user.request1.*;
//import garbege.service.user.service.HeartService;
//import com.petmatz.application.user.KakaoUserCase;
//import garbege.service.user.service.PageService;
//import garbege.service.user.service.UserService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//public class UserController {
//
//    private final UserService userService;
//    private final KakaoUserCase kakaoUserCase;
//    private final PageService pageService;
//    private final HeartService heartService;
//
//
//    @DeleteMapping("/delete-user")
//    public Response<Void> deleteUser(@RequestBody @Valid UserDeleteRequest requestBody) {
//        userService.deleteId(requestBody);
//        return Response.success();
//    }
//
////    @PostMapping("/edit-kakaoprofile")
////    public Response<Void> editKakaoProfile(@RequestBody @Valid EditKakaoProfileRequestDto requestBody) {
////        kakaoUserCase.editKakaoProfile(EditKakaoProfileRequestDto.of(requestBody));
////        return Response.success();
////    }
//
////    @PostMapping("/hearting")
////    public Response<Void> hearting(@RequestBody @Valid HeartingRequestDto requestBody) {
////        heartService.hearting(requestBody);
////        return Response.success();
////    }
////
////    @GetMapping("/get-heartlist") // 이거 테스트 필요
////    public Response<List<HeartedUserDto>> getHeartedList() {
////        List<HeartedUserDto> heartedList = heartService.getHeartedList();
////        return Response.success(heartedList);
////    }
//
////    @GetMapping("/get-myprofile")
////    public Response<MyProfileResponse> getMypage() {
////        MyProfileResponse myPage = pageService.getMypage();
////        return Response.success(myPage);
////    }
//
////    @GetMapping("/get-otherprofile")
////    public Response<OtherProfileResponse> getOtherMypage(@RequestParam @Valid Long userId) {
////        OtherProfileResponse otherMypage = pageService.getOtherMypage(userId);
////        return Response.success(otherMypage);
////    }
//
////    @PostMapping("/edit-myprofile")
////    public Response<EditMyProfileResponse> editMyProfile(@RequestBody @Valid EditMyProfileRequest requestBody) throws MalformedURLException {
////        EditMyProfileResponse editMyProfileResponse = pageService.editMyProfile(EditMyProfileRequest.of(requestBody));
////        return Response.success(editMyProfileResponse);
////    }
//}
