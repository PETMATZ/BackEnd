package com.petmatz.domain.user.response;

import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import com.petmatz.domain.user.constant.PreferredSize;
import com.petmatz.domain.user.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class SignInResponseDto {
    private Long id;
    private String accountId;
    private String nickname;
    private LoginRole loginRole;
    private LoginType loginType;
    private List<PreferredSize> preferredSizes; // 변경된 필드
    private Gender gender;
    private Boolean isRegistered;
    private Integer recommendationCount;
    private Integer careCompletionCount;
    private Boolean isCareAvailable;
    private String mbti;
    private String region;
    private Integer regionCode;

    public SignInResponseDto(User user) {
        this.id = user.getId();
        this.accountId = user.getAccountId();
        this.nickname = user.getNickname();
        this.loginRole = user.getLoginRole();
        this.loginType = user.getLoginType();
        this.preferredSizes = user.getPreferredSizes(); // 리스트를 직접 할당
        this.gender = user.getGender();
        this.isRegistered = user.getIsRegistered();
        this.recommendationCount = user.getRecommendationCount();
        this.careCompletionCount = user.getCareCompletionCount();
        this.isCareAvailable = user.getCareAvailable();
        this.mbti = user.getMbti();
        this.region = user.getRegion();
        this.regionCode=user.getRegionCode();
    }
}