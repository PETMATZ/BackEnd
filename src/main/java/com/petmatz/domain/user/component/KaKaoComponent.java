package com.petmatz.domain.user.component;

import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.EditKakaoProfileInfo;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.EditKakaoProfileResponseDto;
import com.petmatz.domain.user.response.UpdateLocationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KaKaoComponent {

    private final JwtExtractProvider jwtExtractProvider;
    private final UserRepository userRepository;
    private final GeocodingService geocodingService;

    @Transactional
    public ResponseEntity<? super EditKakaoProfileResponseDto> editKakaoProfile(EditKakaoProfileInfo info) {
        try {
            Long userId = jwtExtractProvider.findIdFromJwt();

            // userId가 null인 경우 예외 처리
            if (userId == null) {
                log.warn("JWT에서 추출된 userId가 null입니다.");
                return EditKakaoProfileResponseDto.idNotFound();
            }

            boolean exists = userRepository.existsById(userId);
            if (!exists) {
                log.warn("존재하지 않는 사용자 ID: {}", userId);
                return EditKakaoProfileResponseDto.idNotFound();
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

            GeocodingService.KakaoRegion kakaoRegion = geocodingService.getRegionFromCoordinates(info.getLatitude(), info.getLongitude());
            if (kakaoRegion == null || kakaoRegion.getCodeAsInteger() == null) {
                return UpdateLocationResponseDto.wrongLocation(); // Kakao API 호출 실패 처리
            }

            user.updateKakaoProfile(info, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());
        } catch (Exception e) {
            log.error("프로필 수정 실패", e);
            return EditKakaoProfileResponseDto.editFailed();
        }
        return EditKakaoProfileResponseDto.success();
    }
}
