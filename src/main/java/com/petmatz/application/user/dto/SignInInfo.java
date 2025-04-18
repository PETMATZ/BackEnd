package com.petmatz.application.user.dto;

import com.petmatz.domain.user.User;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Getter;

import java.util.List;

@Getter
public class SignInInfo {
    private Long id;
    private String accountId;
    private String nickname;
    private LoginRole loginRole;
    private LoginType loginType;
    private List<PreferredSize> preferredSizes; // 변경된 필드
    private Gender gender;
    private Integer recommendationCount;
    private Integer careCompletionCount;
    private Boolean isCareAvailable;
    private String mbti;
    private String region;
    private Integer regionCode;
    private String accessCookie;
    private String refreshCookie;

    public SignInInfo(User user, String accessCookie, String refreshCookie) {
        this.id = user.getId();
        this.accountId = user.getAccount().getAccountId();
        this.nickname = user.getProfile().getNickname();
        this.loginRole = user.getAccount().getLoginRole();
        this.loginType = user.getAccount().getLoginType();
        this.preferredSizes = user.getProfile().getPreferredSizes(); // 리스트를 직접 할당
        this.gender = user.getProfile().getGender();
        this.recommendationCount = user.getStats().getRecommendationCount();
        this.careCompletionCount = user.getStats().getCareCompletionCount();
        this.isCareAvailable = user.getProfile().getCareAvailable();
        this.mbti = user.getProfile().getMbti();
        this.region = user.getLocation().getRegion();
        this.regionCode = user.getLocation().getRegionCode();
        this.accessCookie = accessCookie;
        this.refreshCookie = refreshCookie;
    }

}