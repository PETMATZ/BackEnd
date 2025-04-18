package com.petmatz.application.page;

import com.petmatz.application.page.dto.MyPageInfo;
import com.petmatz.application.page.mapper.MyPageMapper;
import com.petmatz.application.page.port.MyPageUseCasePort;
import com.petmatz.domain.old.aws.vo.S3Imge;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserQueryPort;
import garbege.service.user.info.EditMyProfileInfo;
import garbege.service.user.response.EditMyProfileResponseDto;
import garbege.service.user.response.GetOtherProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;

@Service
@RequiredArgsConstructor
public class MyPageUseCase implements MyPageUseCasePort {

    private final UserQueryPort userQueryPort;

    @Override
    public MyPageInfo getMyPage(Long userId) {
        User user = userQueryPort.findById(userId);
        return MyPageMapper.to(user);
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
