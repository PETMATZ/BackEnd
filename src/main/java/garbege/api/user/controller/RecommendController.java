package garbege.api.user.controller;

import com.petmatz.api.global.dto.Response;
import garbege.api.user.request1.UpdateRecommendationRequestDto;
import garbege.service.user.response.GetRecommendationResponseDto;
import garbege.service.user.service.RecommendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
