package com.petmatz.domain.user.service;

import com.petmatz.api.user.request.UpdateRecommendationRequestDto;
import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.component.RecommendComponent;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.domain.user.entity.Recommendation;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.entity.UserFactory;
import com.petmatz.domain.user.repository.RecommendationRepository;
import com.petmatz.domain.user.response.GetRecommendationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendService {

    private final JwtExtractProvider jwtExtractProvider;
    private final RecommendationRepository recommendationRepository;
    private final UserUtils userUtils;
    private final RecommendComponent recommendComponent;

    @Transactional
    public void updateRecommend(UpdateRecommendationRequestDto dto) {
        Long recommendedId = dto.getUserId();
        Long myId = jwtExtractProvider.findIdFromJwt();
        User recommendedUser = userUtils.findIdUser(recommendedId);

        Integer recommendationCount = recommendedUser.getRecommendationCount() + 1;

        recommendedUser.updateRecommendation(recommendationCount);

        Recommendation recommendation = UserFactory.createRecommendation(myId, recommendedId);
        recommendationRepository.save(recommendation);
    }

    public GetRecommendationResponseDto getRecommend(UpdateRecommendationRequestDto dto) {
        Long recommendedId = dto.getUserId();
        Long myId = jwtExtractProvider.findIdFromJwt();

        boolean recommendUser = recommendComponent.validateRecommendUser(myId, recommendedId);
        return new GetRecommendationResponseDto(recommendUser);
    }
}
