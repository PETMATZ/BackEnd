package com.petmatz.persistence.recommendation.repository;

import com.petmatz.persistence.recommendation.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long>{
    Boolean existsByMyIdAndRecommendedId(Long myId, Long recommendedId);
}
