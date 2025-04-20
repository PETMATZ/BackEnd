package com.petmatz.api.auth.dto;

import com.petmatz.application.user.dto.SignInInfo;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Builder;

import java.util.List;

@Builder
public record SignInResponse(
        Long id,
        String accountId,
        String nickname,
        LoginRole loginRole,
        LoginType loginType,
        List<PreferredSize> preferredSizes,
        Gender gender,
        Integer recommendationCount,
        Integer careCompletionCount,
        Boolean isCareAvailable,
        String mbti,
        String region,
        Integer regionCode

) {

    public static SignInResponse to(SignInInfo sign) {
        return SignInResponse.builder()
                .id(sign.getId())
                .accountId(sign.getAccountId())
                .nickname(sign.getNickname())
                .loginRole(sign.getLoginRole())
                .loginType(sign.getLoginType())
                .preferredSizes(sign.getPreferredSizes())
                .gender(sign.getGender())
                .recommendationCount(sign.getRecommendationCount())
                .careCompletionCount(sign.getCareCompletionCount())
                .isCareAvailable(sign.getIsCareAvailable())
                .mbti(sign.getMbti())
                .region(sign.getRegion())
                .regionCode(sign.getRegionCode())
                .build();
    }

}
