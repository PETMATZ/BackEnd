package com.petmatz.domain.user.component;

import com.petmatz.api.user.request.HeartedUserDto;
import com.petmatz.api.user.request.HeartingRequestDto;
import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.entity.Heart;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.entity.UserFactory;
import com.petmatz.domain.user.repository.HeartRepository;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.GetHeartingListResponseDto;
import com.petmatz.domain.user.response.HeartingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeartComponent {

    private final UserRepository userRepository;
    private final JwtExtractProvider jwtExtractProvider;
    private final HeartRepository heartRepository;


    @Transactional
    public ResponseEntity<? super HeartingResponseDto> hearting(HeartingRequestDto dto) {
        try {
            Long heartedId = dto.getHeartedId();

            // 대상 사용자가 존재하는지 확인
            boolean exists = userRepository.existsById(heartedId);
            if (!exists) {
                return HeartingResponseDto.heartedIdNotFound();
            }

            // 현재 사용자 ID 가져오기
            Long userId = jwtExtractProvider.findIdFromJwt();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

            // DB에서 myId와 heartedId로 Heart 레코드 확인
            Optional<Heart> existingHeart = heartRepository.findByMyIdAndHeartedId(user.getId(), heartedId);

            if (existingHeart.isPresent()) {
                // 찜하기 해제 (DB에서 삭제)
                heartRepository.delete(existingHeart.get());
                return HeartingResponseDto.success(); // 찜하기 해제 성공 응답
            }

            // 찜하기 진행 (DB에 저장)
            Heart heart = UserFactory.createHeart(userId, heartedId);
            heartRepository.save(heart);

            return HeartingResponseDto.success(); // 찜하기 성공 응답

        } catch (Exception e) {
            log.info("찜하기 실패: {}", e);
            return HeartingResponseDto.databaseError(); // 데이터베이스 오류 응답
        }
    }


    public ResponseEntity<? super GetHeartingListResponseDto> getHeartedList() {
        try {
            Long userId = jwtExtractProvider.findIdFromJwt();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

            // Heart 리스트 조회
            List<Heart> heartList = heartRepository.findAllByMyId(user.getId());

            // Heart 정보와 관련된 User 데이터 매핑
            List<HeartedUserDto> heartedUsers = heartList.stream()
                    .map(heart -> {
                        User heartedUser = userRepository.findById(heart.getHeartedId())
                                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + heart.getHeartedId()));

                        return new HeartedUserDto(
                                heart.getMyId(),
                                heart.getHeartedId(),
                                heartedUser.getNickname(),
                                heartedUser.getProfileImg(),
                                heartedUser.getCareAvailable(),
                                heartedUser.getPreferredSizes()
                        );
                    })
                    .toList();

            // 성공 응답 반환
            return GetHeartingListResponseDto.success(heartedUsers);

        } catch (Exception e) {
            log.info("찜리스트 받아오기 실패: {}", e);
            return HeartingResponseDto.databaseError();
        }
    }
}
