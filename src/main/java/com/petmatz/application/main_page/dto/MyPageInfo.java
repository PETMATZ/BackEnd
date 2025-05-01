package com.petmatz.application.main_page.dto;

import com.petmatz.domain.user.User;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class MyPageInfo {

    private Long id;
    private String accountId;
    private String nickname;
    private String profileImg;
    private List<PreferredSize> preferredSizes; // 변경: List로 수정
    private Gender gender;
    private String introduction;
    private Integer recommendationCount;
    private Integer careCompletionCount;
    private Boolean isCareAvailable;
    private String mbti;
    private String region;
    private Integer regionCode;

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
