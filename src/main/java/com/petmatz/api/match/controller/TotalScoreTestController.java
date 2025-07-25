//package com.petmatz.api.match.controller;
//
//import com.petmatz.api.global.dto.Response;
//import com.petmatz.api.match.request.UserIdRequest;
//import com.petmatz.domain.old.match.dto.response.UserResponse;
//import com.petmatz.domain.old.match.service.MatchScoreService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/total-score")
//@RequiredArgsConstructor
//public class TotalScoreTestController {
//
//    private final MatchScoreService matchScoreService;
//
//    @PostMapping("/calculate")
//    public void calculateTotalScore() {
//        matchScoreService.calculateTotalScore();
//    }
//
//    @PostMapping("/get-users")
//    public Response<List<UserResponse>> getUsersWithinBoundingBox(@RequestBody UserIdRequest userId) {
//        List<UserResponse> usersWithinBoundingBox = matchScoreService.getUsersWithinBoundingBox(userId.userId());
//        return Response.success(usersWithinBoundingBox);
//    }
//}
//
