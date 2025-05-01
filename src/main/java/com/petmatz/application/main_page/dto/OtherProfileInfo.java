package com.petmatz.application.main_page.dto;

import com.petmatz.domain.user.User;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OtherProfileInfo {

    private final Long id;
    private final String accountId;
    private final String nickname;
    private final String profileImg;
    private final List<PreferredSize> preferredSize;
    private final Gender gender;
    private final Boolean isRegistered;
    private final Integer recommendationCount;
    private final Integer careCompletionCount;
    private final Boolean isCareAvailable;
    private final String introduction;
    private final String mbti;
    private final String region;
    private final Integer regionCode;
    private final boolean isMyHeartUser;

    public static OtherProfileInfo to(User user, boolean isMyHeartUser) {
        return OtherProfileInfo.builder()
                .id(user.getId())
                .accountId(user.getAccount().getAccountId())
                .nickname(user.getProfile().getNickname())
                .profileImg(user.getProfile().getProfileImg())
                .preferredSize(user.getProfile().getPreferredSizes())
                .gender(user.getProfile().getGender())
                .recommendationCount(user.getStats().getRecommendationCount())
                .careCompletionCount(user.getStats().getCareCompletionCount())
                .introduction(user.getProfile().getIntroduction())
                .isCareAvailable(user.getProfile().getCareAvailable())
                .mbti(user.getProfile().getMbti())
                .region(user.getLocation().getRegion())
                .isMyHeartUser(isMyHeartUser)
                .regionCode(user.getLocation().getRegionCode())
                .build();
    }

}
