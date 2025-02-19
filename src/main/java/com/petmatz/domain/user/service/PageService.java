package com.petmatz.domain.user.service;

import com.petmatz.common.security.jwt.JwtExtractProvider;
import com.petmatz.domain.aws.AwsClient;
import com.petmatz.domain.aws.vo.S3Imge;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.EditMyProfileInfo;
import com.petmatz.domain.user.response.EditMyProfileResponseDto;
import com.petmatz.domain.user.response.GetMyProfileResponseDto;
import com.petmatz.domain.user.response.GetOtherProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;

@Component
@RequiredArgsConstructor
public class PageService {

    private final JwtExtractProvider jwtExtractProvider;
    private final AwsClient awsClient;
    private final UserUtils userUtils;


    public GetMyProfileResponseDto getMypage() {
        String userId = jwtExtractProvider.findAccountIdFromJwt();
        User user = userUtils.findUser(userId);
        return new GetMyProfileResponseDto(user);
    }

    public GetOtherProfileResponseDto getOtherMypage(Long userId) {
        Long myId = jwtExtractProvider.findIdFromJwt();
        User user = userUtils.findIdUser(myId);

        // 조회 대상 사용자가 존재하는지 확인
        userUtils.findIdUser(userId);

        // 현재 로그인한 사용자가 조회 대상 사용자를 찜했는지 확인
        boolean checkHeart = userUtils.checkHeart(myId, userId);

        return new GetOtherProfileResponseDto(user, checkHeart);
    }

    @Transactional
    public EditMyProfileResponseDto editMyProfile(EditMyProfileInfo info) throws MalformedURLException {
            Long userId = jwtExtractProvider.findIdFromJwt();
            String userEmail = jwtExtractProvider.findAccountIdFromJwt();
            User user = userUtils.findIdUser(userId);

            //Img 정제
            S3Imge petImg = awsClient.UploadImg(userEmail, info.getProfileImg(), "CUSTOM_USER_IMG", null);

            // 병합된 DTO를 기반으로 엔티티 생성
            String resultImg = user.updateImgURL(info.getProfileImg(), petImg);
            user.updateProfile(info);

            return new EditMyProfileResponseDto(resultImg);
    }
}
