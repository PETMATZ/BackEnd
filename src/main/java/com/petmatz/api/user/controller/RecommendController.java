package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.UpdateRecommendationRequestDto;
import com.petmatz.domain.user.response.GetRecommendationResponseDto;
import com.petmatz.domain.user.response.UpdateRecommendationResponseDto;
import com.petmatz.domain.user.service.RecommendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/update-recommendation")
    public Response<Void> updateRecommend(@RequestBody @Valid UpdateRecommendationRequestDto requestBody) {
        recommendService.updateRecommend(requestBody);
        return Response.success();
    }

    @GetMapping("/get-recommended")
    public Response<GetRecommendationResponseDto> getRecommend(@RequestBody @Valid UpdateRecommendationRequestDto requestBody) {
        GetRecommendationResponseDto recommend = recommendService.getRecommend(requestBody);
        return Response.success(recommend);
    }
}
