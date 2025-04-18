package com.petmatz.application.page.mapper;

import com.petmatz.application.page.dto.MyPageInfo;
import com.petmatz.domain.user.User;

public class MyPageMapper {

    public static MyPageInfo to(User user) {
        return MyPageInfo.builder()
                .id(user.getId())
                .accountId(user.getAccount().getAccountId())
                .nickname(user.getProfile().getNickname())
                .profileImg(user.getProfile().getProfileImg())
                .preferredSizes(user.getProfile().getPreferredSizes())
                .gender(user.getProfile().getGender())
                .introduction(user.getProfile().getIntroduction())
                .recommendationCount(user.getStats().getRecommendationCount())
                .careCompletionCount(user.getStats().getCareCompletionCount())
                .isCareAvailable(user.getProfile().getCareAvailable())
                .mbti(user.getProfile().getMbti())
                .region(user.getLocation().getRegion())
                .regionCode(user.getLocation().getRegionCode())
                .build();
    }

}
