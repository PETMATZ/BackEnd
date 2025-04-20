package com.petmatz.api.user.dto.response;

import com.petmatz.application.page.dto.OtherProfileInfo;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OtherProfileResponse {
    private final Long id;
    private final String accountId;
    private final String nickname;
    private final String profileImg;
    private final List<PreferredSize> preferredSize;
    private final Gender gender;
    private final Integer recommendationCount;
    private final Integer careCompletionCount;
    private final Boolean isCareAvailable;
    private final String introduction;
    private final String mbti;
    private final String region;
    private final Integer regionCode;
    private final boolean isMyHeartUser;

    public static OtherProfileResponse to(OtherProfileInfo otherProfileInfo) {
        return OtherProfileResponse.builder()
                .id(otherProfileInfo.getId())
                .accountId(otherProfileInfo.getAccountId())
                .nickname(otherProfileInfo.getNickname())
                .profileImg(otherProfileInfo.getProfileImg())
                .preferredSize(otherProfileInfo.getPreferredSize())
                .gender(otherProfileInfo.getGender())
                .recommendationCount(otherProfileInfo.getRecommendationCount())
                .careCompletionCount(otherProfileInfo.getCareCompletionCount())
                .introduction(otherProfileInfo.getIntroduction())
                .mbti(otherProfileInfo.getMbti())
                .region(otherProfileInfo.getRegion())
                .regionCode(otherProfileInfo.getRegionCode())
                .isMyHeartUser(otherProfileInfo.isMyHeartUser())
                .build();
    }

}
