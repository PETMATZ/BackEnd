package com.petmatz.api.user.dto.response;

import com.petmatz.application.main_page.dto.MyPageInfo;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Getter;

import java.util.List;

@Getter
public class MyProfileResponse {
    private final Long id;
    private final String accountId;
    private final String nickname;
    private final String profileImg;
    private final List<PreferredSize> preferredSizes; // 변경: List로 수정
    private final Gender gender;
    private final String introduction;
    private final Integer recommendationCount;
    private final Integer careCompletionCount;
    private final Boolean isCareAvailable;
    private final String mbti;
    private final String region;
    private final Integer regionCode;

    public MyProfileResponse(MyPageInfo myPageInfo) {
        this.id = myPageInfo.getId();
        this.accountId = myPageInfo.getAccountId();
        this.nickname = myPageInfo.getNickname();
        this.profileImg = myPageInfo.getProfileImg();
        this.preferredSizes = myPageInfo.getPreferredSizes(); // 수정: 리스트 그대로 할당
        this.gender = myPageInfo.getGender();
        this.introduction=myPageInfo.getIntroduction();
        this.recommendationCount = myPageInfo.getRecommendationCount();
        this.careCompletionCount = myPageInfo.getCareCompletionCount();
        this.isCareAvailable = myPageInfo.getIsCareAvailable();
        this.mbti = myPageInfo.getMbti();
        this.region = myPageInfo.getRegion();
        this.regionCode= myPageInfo.getRegionCode();
    }
//
//    public static ResponseEntity<LogInResponseDto> idNotFound() {
//        LogInResponseDto responseBody = new LogInResponseDto(ResponseCode.ID_NOT_FOUND, ResponseMessage.ID_NOT_FOUND);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
//    }
//
//    public static ResponseEntity<GetMyProfileResponseDto> success(User user) { // 반환 타입 수정
//        GetMyProfileResponseDto responseBody = new GetMyProfileResponseDto(user);
//        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
//    }
}