package com.petmatz.domain.user.component;

import com.petmatz.api.user.request.UpdateRecommendationRequestDto;
import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.entity.Recommendation;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.entity.UserFactory;
import com.petmatz.domain.user.repository.RecommendationRepository;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.GetRecommendationResponseDto;
import com.petmatz.domain.user.response.UpdateRecommendationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendComponent {

    private final JwtExtractProvider jwtExtractProvider;
    private final UserRepository userRepository;
    private final RecommendationRepository recommendationRepository;


    @Transactional
    public ResponseEntity<? super UpdateRecommendationResponseDto> updateRecommend(UpdateRecommendationRequestDto dto) {
        try {
            Long recommendedId = dto.getUserId();
            Long myId = jwtExtractProvider.findIdFromJwt();
            User recommendedUser = userRepository.findById(recommendedId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + recommendedId));

            boolean exists = userRepository.existsById(recommendedId);
            if (!exists) {
                return UpdateRecommendationResponseDto.userNotFound();
            }
            Integer recommendationCount = recommendedUser.getRecommendationCount() + 1;

            recommendedUser.updateRecommendation(recommendationCount);

            Recommendation recommendation = UserFactory.createRecommendation(myId, recommendedId);
            recommendationRepository.save(recommendation);

        } catch (Exception e) {
            log.info("추천수 업데이트 실패: {}", e);
            return UpdateRecommendationResponseDto.userNotFound();
        }
        return UpdateRecommendationResponseDto.success();
    }

    public ResponseEntity<? super GetRecommendationResponseDto> getRecommend(UpdateRecommendationRequestDto dto) {
        try {
            Long recommendedId = dto.getUserId();
            Long myId = jwtExtractProvider.findIdFromJwt();

            boolean exists = recommendationRepository.existsByMyIdAndRecommendedId(myId,recommendedId);
            return GetRecommendationResponseDto.success(exists);

        } catch (Exception e) {
            log.info("추천수 업데이트 실패: {}", e);
            return GetRecommendationResponseDto.userNotFound();
        }
    }

}
