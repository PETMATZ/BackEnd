package com.petmatz.domain.recommendation.port;

import com.petmatz.domain.recommendation.Recommendation;

public interface RecommendationCommandPort {

    void save(Recommendation recommendation);

}
