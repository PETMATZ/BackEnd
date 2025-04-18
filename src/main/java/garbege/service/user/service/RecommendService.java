package garbege.service.user.service;

import garbege.api.user.request1.UpdateRecommendationRequestDto;
import garbege.service.user.component.RecommendComponent;
import garbege.service.user.provider.UserUtils;
import com.petmatz.garbege.service.user.Recommendation;
import com.petmatz.garbege.service.user.User;
import com.petmatz.domain.user.utils.UserFactory;
import com.petmatz.persistence.recommendation.repository.RecommendationRepository;
import garbege.service.user.response.GetRecommendationResponseDto;
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
