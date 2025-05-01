package com.petmatz.application.recommendation;

import com.petmatz.application.recommendation.port.RecommendationUseCasePort;
import com.petmatz.domain.recommendation.Recommendation;
import com.petmatz.domain.recommendation.port.RecommendationCommandPort;
import com.petmatz.domain.recommendation.port.RecommendationQueryPort;
import com.petmatz.domain.user.port.UserCommandPort;
import com.petmatz.domain.user.utils.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationUseCase implements RecommendationUseCasePort {

    private final UserCommandPort userCommandPort;
    private final RecommendationCommandPort recommendationCommandPort;
    private final RecommendationQueryPort recommendationQueryPort;

    public void updateRecommend(Long recommendedId, Long myId) {
        userCommandPort.updateRecommendation(recommendedId);
        Recommendation recommendation = UserFactory.createRecommendation(myId, recommendedId);
        recommendationCommandPort.save(recommendation);
    }

    @Override
    public boolean getRecommend(Long myId, Long recommendedId) {
        return recommendationQueryPort.existsByMyIdAndRecommendedId(myId, recommendedId);
    }

}
