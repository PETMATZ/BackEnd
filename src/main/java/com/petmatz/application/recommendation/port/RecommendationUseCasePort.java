package com.petmatz.application.recommendation.port;


public interface RecommendationUseCasePort {

    void updateRecommend(Long userId, Long myId);

    boolean getRecommend(Long myId, Long recommendedId);

}
