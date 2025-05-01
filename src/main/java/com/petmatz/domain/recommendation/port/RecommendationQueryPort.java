package com.petmatz.domain.recommendation.port;

public interface RecommendationQueryPort {

    Boolean existsByMyIdAndRecommendedId(Long myId, Long recommendedId);
}
