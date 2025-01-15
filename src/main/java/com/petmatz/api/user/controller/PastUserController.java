//package com.petmatz.api.user.controller;
//
//import com.petmatz.api.user.request.*;
//import com.petmatz.domain.user.service.AuthService;
//import com.petmatz.domain.user.response.*;
//import com.petmatz.domain.user.service.UserService;
//import com.petmatz.user.common.LogInResponseDto;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.MalformedURLException;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//public class PastUserController {
//    private final UserService userService;
//    private final AuthService authService;
//
//    @PostMapping("/email-certification")
//    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(@RequestBody @Valid EmailCertificationRequestDto requestBody) {
//        ResponseEntity<? super EmailCertificationResponseDto> response = userService.emailCertification(requestBody);
//        log.info("[emailCertification]: { accountId: " + requestBody.getAccountId() + "}");
//        return response;
//    } // 하나 했음
//
//    @PostMapping("/check-certification")
//    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(@RequestBody @Valid CheckCertificationRequestDto requestBody) {
//        ResponseEntity<? super CheckCertificationResponseDto> response = userService.checkCertification(CheckCertificationRequestDto.of(requestBody));
//        log.info("[checkCertification]: {accountId: " + requestBody.getAccountId() + ", certificationNumber: " + requestBody.getCertificationNumber() + "}");
//        return response;
//    } // 했음
//
//    @PostMapping("/sign-up")
//    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) throws MalformedURLException {
//        SignUpResponseDto responseDto = authService.signUp(SignUpRequestDto.of(requestBody));
//        log.info("[signUp]: { accountId: " + requestBody.getAccountId() + ", password: " + requestBody.getPassword());
//        return ResponseEntity.ok(responseDto);
//    } // o
//
//    @PostMapping("/sign-in")
//    public ResponseEntity<? super SignInResponseDto> signIn(
//            @RequestBody @Valid SignInRequestDto requestBody,
//            HttpServletResponse response) {
//
//        ResponseEntity<? super SignInResponseDto> result = userService.signIn(SignInRequestDto.of(requestBody), response);
//        log.info("[signIn]: { accountId: " + requestBody.getAccountId() + ", result: " + result.getStatusCode() + " }");
//        return result;
//    } // o
//
//    @PostMapping("/delete-user")
//    public ResponseEntity<? super DeleteIdResponseDto> deleteUser(@RequestBody @Valid DeleteIdRequestDto requestBody) {
//        ResponseEntity<? super DeleteIdResponseDto> response = userService.deleteId(requestBody);
//        log.info("[deleteUser]:{password: " + requestBody.getPassword() + "}");
//        return response;
//    } // o
//
//    //---------------------------------------------------------------------------------------------------------------------------------//
//    @GetMapping("/get-myprofile")
//    public ResponseEntity<? super GetMyProfileResponseDto> getMypage() {
//        ResponseEntity<? super GetMyProfileResponseDto> response = userService.getMypage();
//        log.info("[getMypage]");
//        return response;
//    } // o
//
//    @GetMapping("/get-otherprofile")
//    public ResponseEntity<? super GetOtherProfileResponseDto> getOtherMypage(@RequestParam @Valid Long userId) {
//        ResponseEntity<? super GetOtherProfileResponseDto> response = userService.getOtherMypage(userId);
//        log.info("[getOtherMypage]");
//        return response;
//    } // o
//
//    @PostMapping("/edit-myprofile")
//    public ResponseEntity<? super EditMyProfileResponseDto> editMyProfile(@RequestBody @Valid EditMyProfileRequestDto requestBody) {
//        ResponseEntity<? super EditMyProfileResponseDto> response = userService.editMyProfile(EditMyProfileRequestDto.of(requestBody));
//        log.info("[editMyProfile]");
//        return response;
//    } // o
//
//    @PostMapping("/send-repassword")
//    public ResponseEntity<? super SendRepasswordResponseDto> sendRepassword(@RequestBody @Valid SendRepasswordRequestDto requestBody) {
//        ResponseEntity<? super SendRepasswordResponseDto> response = userService.sendRepassword(requestBody);
//        log.info("[sendRepassword]: {accountId: " + requestBody.getAccountId() + "}");
//        return response;
//    } // o
//
//    @PostMapping("/repassword")
//    public ResponseEntity<? super RepasswordResponseDto> repassword(@RequestBody @Valid RepasswordRequestDto requestBody) {
//        ResponseEntity<? super RepasswordResponseDto> response = userService.repassword(RepasswordRequestDto.of(requestBody));
//        log.info("[repassword]: {currentPassword: " + requestBody.getCurrentPassword() + ", newPassword: " + requestBody.getNewPassword() + "}");
//        return response;
//    } // o
//
//    //---------------------------------------------------------------------------------------------------------------------------------------------------------------
//    @PostMapping("/hearting")
//    public ResponseEntity<? super HeartingResponseDto> hearting(@RequestBody @Valid HeartingRequestDto requestBody) {
//        ResponseEntity<? super HeartingResponseDto> response = userService.hearting(requestBody);
//        log.info("[hearting]: {heartedId: " + requestBody.getHeartedId() + "}");
//        return response;
//    } // o
//
//    @GetMapping("/get-heartlist")
//    public ResponseEntity<? super GetHeartingListResponseDto> getHeartedList() {
//        ResponseEntity<? super GetHeartingListResponseDto> response = userService.getHeartedList();
//        log.info("[getHeartedList]");
//        return response;
//    } // o
//
//    @PostMapping("/update-location")
//    public ResponseEntity<? super UpdateLocationResponseDto> updateLocation(@RequestBody @Valid UpdateLocationRequestDto requestBody) {
//        ResponseEntity<? super UpdateLocationResponseDto> response = userService.updateLocation(UpdateLocationRequestDto.of(requestBody));
//        log.info("[updateLocation]");
//        return response;
//    } // o
//
//    @PostMapping("/update-recommendation")
//    public ResponseEntity<? super UpdateRecommendationResponseDto> updateRecommend(@RequestBody @Valid UpdateRecommendationRequestDto requestBody) {
//        ResponseEntity<? super UpdateRecommendationResponseDto> response = userService.updateRecommend(requestBody);
//        log.info("[updateRecommend]");
//        return response;
//    }
//
//    @GetMapping("/get-recommended")
//    public ResponseEntity<? super GetRecommendationResponseDto> getRecommend(@RequestBody @Valid UpdateRecommendationRequestDto requestBody) {
//        ResponseEntity<? super GetRecommendationResponseDto> response = userService.getRecommend(requestBody);
//        log.info("[getRecommend]");
//        return response;
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<? super LogInResponseDto> logout(HttpServletResponse res) {
//        ResponseEntity<? super LogInResponseDto> response = userService.logout(res);
//        log.info("[logout]");
//        return response;
//    }
//
//    @PostMapping("/edit-kakaoprofile")
//    public ResponseEntity<? super EditKakaoProfileResponseDto> editKakaoProfile(@RequestBody @Valid EditKakaoProfileRequestDto requestBody) {
//        ResponseEntity<? super EditKakaoProfileResponseDto> response = userService.editKakaoProfile(EditKakaoProfileRequestDto.of(requestBody));
//        log.info("[editKakaoProfile]");
//        return response;
//    }
//}
//
