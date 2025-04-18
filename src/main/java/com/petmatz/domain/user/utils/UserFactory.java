package com.petmatz.domain.user.utils;



import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import com.petmatz.domain.recommendation.Recommendation;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.model.Account;
import com.petmatz.domain.user.model.Location;
import com.petmatz.domain.user.model.Profile;
import com.petmatz.domain.user.model.UserState;
import com.petmatz.application.user.dto.SignUpInfo;

public class UserFactory {

    public static User createNewUser(SignUpInfo info, String region, Integer regionCode, String imgURL) {
        return User.builder()
                .account(fromAccountDomain(info))
                .profile(fromProfileDomain(info, imgURL))
                .location(fromLocationDomain(info.getLatitude(), info.getLongitude(), region, regionCode))
                .stats(fromStatsDomain())
                .build();
    }

    public static Recommendation createRecommendation(Long myId, Long recommendedId) {
        return Recommendation.builder()
                .myId(myId)
                .recommendedId(recommendedId)
                .build();
    }

    private static Account fromAccountDomain(SignUpInfo info) {
        return Account.builder()
                .accountId(info.getAccountId())
                .registrationId("null")
                .password(info.getPassword())
                .loginRole(LoginRole.ROLE_USER)
                .loginType(LoginType.NORMAL)
                .build();
    }

    private static Profile fromProfileDomain(SignUpInfo info, String imgURl) {
        return Profile.builder()
                .nickname(info.getNickname())
                .profileImg(imgURl)
                .introduction(info.getIntroduction())
                .mbti(info.getMbti())
                .gender(info.getGender())
                .preferredSizes(info.getPreferredSizes())
                .careAvailable(info.getIsCareAvailable())
                .build();
    }

    private static Location fromLocationDomain(Double latitude, Double longitude, String region, Integer regionCode) {
        return Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .region(region)
                .regionCode(regionCode)
                .build();
    }

    private static UserState fromStatsDomain() {
        return UserState.builder()
                .recommendationCount(0)
                .careCompletionCount(0)
                .build();
    }

}