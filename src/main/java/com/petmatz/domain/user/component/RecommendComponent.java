package com.petmatz.domain.user.component;

import com.petmatz.domain.user.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendComponent {

    private final RecommendationRepository recommendationRepository;

    public boolean validateRecommendUser(Long myId, Long recommendUserId) {
        boolean exists = recommendationRepository.existsByMyIdAndRecommendedId(myId, recommendUserId);
        if (exists) {
            return true;
        }
        return false;
    }
}
