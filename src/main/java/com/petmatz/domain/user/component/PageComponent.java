package com.petmatz.domain.user.component;

import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.aws.AwsClient;
import com.petmatz.domain.aws.vo.S3Imge;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.EditMyProfileInfo;
import com.petmatz.domain.user.repository.HeartRepository;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.EditMyProfileResponseDto;
import com.petmatz.domain.user.response.GetMyProfileResponseDto;
import com.petmatz.domain.user.response.GetOtherProfileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PageComponent {

    /**
     * 공통 부분 좀 빼야 할 것 같음
     */

    private final JwtExtractProvider jwtExtractProvider;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;
    private final AwsClient awsClient;


    public ResponseEntity<? super GetMyProfileResponseDto> getMypage() {
        try {
            String userId = jwtExtractProvider.findAccountIdFromJwt();
            User user = userRepository.findByAccountId(userId);

            boolean exists = userRepository.existsByAccountId(userId);
            if (!exists) {
                return GetMyProfileResponseDto.idNotFound();
            }

            return GetMyProfileResponseDto.success(user);

        } catch (Exception e) {
            e.printStackTrace();
            return GetMyProfileResponseDto.databaseError();
        }
    }

    public ResponseEntity<? super GetOtherProfileResponseDto> getOtherMypage(Long userId) {
        try {
            // 현재 로그인한 사용자 ID 가져오기
            Long myId = jwtExtractProvider.findIdFromJwt();
            if (!userRepository.existsById(myId)) {
                return GetMyProfileResponseDto.idNotFound();
            }

            // 조회 대상 사용자 정보 가져오기
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

            // 조회 대상 사용자가 존재하는지 확인
            boolean exists = userRepository.existsById(userId);
            if (!exists) {
                return GetOtherProfileResponseDto.userNotFound();
            }

            // 현재 로그인한 사용자가 조회 대상 사용자를 찜했는지 확인
            boolean isMyHeartUser = heartRepository.existsByMyIdAndHeartedId(myId, userId);

            // 응답 생성
            return GetOtherProfileResponseDto.success(user, isMyHeartUser);

        } catch (Exception e) {
            e.printStackTrace();
            return GetOtherProfileResponseDto.userNotFound();
        }
    }

    //TODO 고쳐야함. ...?
    @Transactional
    public ResponseEntity<? super EditMyProfileResponseDto> editMyProfile(EditMyProfileInfo info) {
        try {
            System.out.println("info ::" + info.isCareAvailable());
            Long userId = jwtExtractProvider.findIdFromJwt();
            String userEmail = jwtExtractProvider.findAccountIdFromJwt();
            boolean exists = userRepository.existsById(userId);
            if (!exists) {
                return EditMyProfileResponseDto.idNotFound();
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

            //6-1 Img 정제
            S3Imge petImg = awsClient.UploadImg(userEmail, info.getProfileImg(), "CUSTOM_USER_IMG", null);

            // 병합된 DTO를 기반으로 엔티티 생성
            String resultImg = user.updateImgURL(info.getProfileImg(), petImg);
            user.updateProfile(info);

//            반환해야 함 아래꺼
            return EditMyProfileResponseDto.success(resultImg);

        } catch (Exception e) {
            log.info("프로필 수정 실패: {}", e);
            return EditMyProfileResponseDto.editFailed();
        }
    }
}
