package com.petmatz.persistence.user.mapper;

import com.petmatz.domain.user.User;
import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import com.petmatz.domain.user.model.Account;
import com.petmatz.domain.user.model.Location;
import com.petmatz.domain.user.model.Profile;
import com.petmatz.domain.user.model.UserState;
import com.petmatz.persistence.user.*;

public class UserDomainMapper {

    public static User fromDomain(UserEntity entity) {
        return User.builder()
                .account(fromAccountDomain(entity.getAccountEntity()))
                .profile(fromProfileDomain(entity.getProfileEntity()))
                .location(fromLocationDomain(entity.getLocationEntity()))
                .stats(fromStatsDomain(entity.getUserStatsEntity()))
                .build();
    }

    private static Account fromAccountDomain(AccountEntity entity) {
        return Account.builder()
                .accountId(entity.getAccountId())
                .registrationId(entity.getRegistrationId())
                .password(entity.getPassword())
                .loginRole(entity.getLoginRole())
                .loginType(entity.getLoginType())
                .build();
    }

    private static Profile fromProfileDomain(ProfileEntity entity) {
        return Profile.builder()
                .nickname(entity.getNickname())
                .profileImg(entity.getProfileImg())
                .introduction(entity.getIntroduction())
                .mbti(entity.getMbti())
                .gender(entity.getGender())
                .preferredSizes(entity.getPreferredSizes())
                .careAvailable(entity.getCareAvailable())
                .build();
    }

    private static Location fromLocationDomain(LocationEntity entity) {
        return Location.builder()
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .region(entity.getRegion())
                .regionCode(entity.getRegionCode())
                .build();
    }

    private static UserState fromStatsDomain(UserStatsEntity entity) {
        return UserState.builder()
                .recommendationCount(entity.getRecommendationCount())
                .careCompletionCount(entity.getCareCompletionCount())
                .build();
    }

}
