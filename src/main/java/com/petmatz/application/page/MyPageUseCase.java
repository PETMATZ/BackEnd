package com.petmatz.application.page;

import com.petmatz.application.page.dto.MyPageInfo;
import com.petmatz.application.page.dto.OtherProfileInfo;
import com.petmatz.application.page.port.MyPageUseCasePort;
import com.petmatz.domain.heart.port.HeartQueryPort;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserCommandPort;
import com.petmatz.domain.user.port.UserQueryPort;
import com.petmatz.application.page.dto.EditMyProfileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageUseCase implements MyPageUseCasePort {

    private final UserQueryPort userQueryPort;
    private final HeartQueryPort heartQueryPort;
    private final UserCommandPort userCommandPort;

    @Override
    public MyPageInfo getMyPage(Long userId) {
        User user = userQueryPort.findById(userId);
        return MyPageInfo.to(user);
    }

    @Override
    public OtherProfileInfo getOtherMypage(Long userId, Long myId) {
        User user = userQueryPort.findById(myId);
        // 조회 대상 사용자가 존재하는지 확인
        userQueryPort.findById(userId);
        // 현재 로그인한 사용자가 조회 대상 사용자를 찜했는지 확인
        boolean checkHeart = heartQueryPort.existsByMyIdAndHeartedId(myId, userId);
        return OtherProfileInfo.to(user,checkHeart);
    }

    @Override
    public String editMyProfile(EditMyProfileInfo info, Long userId, String userEmail){

        User user = userQueryPort.findById(userId);

        //TODO AWS S3 버켓 정상화 되면 고치기
        //Img 정제
//        S3Imge petImg = awsClient.UploadImg(userEmail, info.getProfileImg(), "CUSTOM_USER_IMG", null);
        // 병합된 DTO를 기반으로 엔티티 생성
//        String resultImg = user.updateImgURL(info.getProfileImg(), petImg);

        user.updateUserInfo(info.getNickname(), info.getPreferredSizes(), info.getIntroduction(), info.isCareAvailable(), null);
        userCommandPort.updateUser(user, userId);

        return "test";
    }
}
