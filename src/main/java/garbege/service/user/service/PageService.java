//package garbege.service.user.service;
//
//import com.petmatz.domain.old.aws.AwsClient;
//import com.petmatz.domain.old.aws.vo.S3Imge;
//import com.petmatz.garbege.service.user.User;
//import com.petmatz.application.page.dto.EditMyProfileInfo;
//import com.petmatz.api.user.dto.response.EditMyProfileResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.net.MalformedURLException;
//
//@Component
//@RequiredArgsConstructor
//public class PageService {
//
//    private final JwtExtractProvider jwtExtractProvider;
//    private final AwsClient awsClient;
//    private final UserUtils userUtils;
//
//
//    public MyProfileResponse getMypage() {
//        String userId = jwtExtractProvider.findAccountIdFromJwt();
//        User user = userUtils.findUser(userId);
//        return new MyProfileResponse(user);
//    }
//
//    public OtherProfileResponse getOtherMypage(Long userId) {
//        Long myId = jwtExtractProvider.findIdFromJwt();
//        User user = userUtils.findIdUser(myId);
//
//        // 조회 대상 사용자가 존재하는지 확인
//        userUtils.findIdUser(userId);
//
//        // 현재 로그인한 사용자가 조회 대상 사용자를 찜했는지 확인
//        boolean checkHeart = userUtils.checkHeart(myId, userId);
//
//        return new OtherProfileResponse(user, checkHeart);
//    }
//
//    @Transactional
//    public EditMyProfileResponse editMyProfile(EditMyProfileInfo info) throws MalformedURLException {
//            Long userId = jwtExtractProvider.findIdFromJwt();
//            String userEmail = jwtExtractProvider.findAccountIdFromJwt();
//            User user = userUtils.findIdUser(userId);
//
//            //Img 정제
//            S3Imge petImg = awsClient.UploadImg(userEmail, info.getProfileImg(), "CUSTOM_USER_IMG", null);
//
//            // 병합된 DTO를 기반으로 엔티티 생성
//            String resultImg = user.updateImgURL(info.getProfileImg(), petImg);
//            user.updateProfile(info);
//
//            return new EditMyProfileResponse(resultImg);
//    }
//}
