package com.petmatz.persistence.recommendation.adapter;

import com.petmatz.domain.recommendation.Recommendation;
import com.petmatz.domain.recommendation.port.RecommendationCommandPort;
import com.petmatz.domain.recommendation.port.RecommendationQueryPort;
import com.petmatz.persistence.recommendation.RecommendationEntity;
import com.petmatz.persistence.recommendation.repository.RecommendationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Transactional
public class RecommendationAdapter implements RecommendationQueryPort, RecommendationCommandPort {

    private final RecommendationRepository recommendationRepository;

    @Override
    public void save(Recommendation recommendation) {
        RecommendationEntity recommendationEntity = new RecommendationEntity(recommendation.getMyId(), recommendation.getRecommendedId());
        recommendationRepository.save(recommendationEntity);
    }

    @Override
    public Boolean existsByMyIdAndRecommendedId(Long myId, Long recommendedId) {
        return recommendationRepository.existsByMyIdAndRecommendedId(myId, recommendedId);
    }
}
