package com.petmatz.api.recommendation;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.recommendation.dto.UpdateRecommendationRequestDto;
import com.petmatz.application.recommendation.port.RecommendationUseCasePort;
import com.petmatz.infra.jwt.JwtExtractProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final RecommendationUseCasePort recommendationUseCasePort;
    private final JwtExtractProvider jwtExtractProvider;


    @PostMapping("/update")
    public Response<Void> updateRecommend(@RequestBody @Valid UpdateRecommendationRequestDto requestBody) {
        Long myId = jwtExtractProvider.findIdFromJwt();
        recommendationUseCasePort.updateRecommend(requestBody.getRecommendedId(),myId);
        return Response.success();
    }

    @GetMapping("")
    public Response<Boolean> getRecommend(@RequestBody @Valid UpdateRecommendationRequestDto requestBody) {
        Long myId = jwtExtractProvider.findIdFromJwt();
        boolean recommend = recommendationUseCasePort.getRecommend(myId, requestBody.getRecommendedId());
        return Response.success(recommend);
    }

}
