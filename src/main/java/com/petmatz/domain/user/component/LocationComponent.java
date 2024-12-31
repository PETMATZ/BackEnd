package com.petmatz.domain.user.component;

import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.UpdateLocationInfo;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.UpdateLocationResponseDto;
import com.petmatz.domain.user.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationComponent {

    private final JwtExtractProvider jwtExtractProvider;
    private final UserRepository userRepository;
    private final GeocodingService geocodingService;

    @Transactional
    public ResponseEntity<? super UpdateLocationResponseDto> updateLocation(UpdateLocationInfo info) {
        try {
            // JWT에서 사용자 ID 추출
            Long userId = jwtExtractProvider.findIdFromJwt();

            // 사용자 엔티티 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

            // 사용자 존재 여부 확인
            boolean exists = userRepository.existsById(userId);
            if (!exists) {
                return UpdateLocationResponseDto.userNotFound();
            }

            // GeocodingService에서 지역명과 행정코드 가져오기
            GeocodingService.KakaoRegion kakaoRegion = geocodingService.getRegionFromCoordinates(info.getLatitude(), info.getLongitude());
            if (kakaoRegion == null || kakaoRegion.getCodeAsInteger() == null) {
                return UpdateLocationResponseDto.wrongLocation(); // Kakao API 호출 실패 처리
            }

            user.updateLocation(info, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());

            // 성공 응답 반환
            return UpdateLocationResponseDto.success(kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());
        } catch (Exception e) {
            log.error("위치 업데이트 실패: {}", e.getMessage(), e);
            return UpdateLocationResponseDto.wrongLocation();
        }
    }
}
