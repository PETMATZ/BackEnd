package com.petmatz.persistence.user.mapper;


import com.petmatz.domain.user.User;
import com.petmatz.domain.user.model.Account;
import com.petmatz.domain.user.model.Location;
import com.petmatz.domain.user.model.Profile;
import com.petmatz.domain.user.model.UserState;
import com.petmatz.persistence.user.*;

// persistence.user.mapper.UserMapper.java
public class UserEntityMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .accountEntity(toAccountEntity(user.getAccount()))
                .profileEntity(toProfileEntity(user.getProfile()))
                .locationEntity(toLocationEntity(user.getLocation()))
                .userStatsEntity(toStatsEntity(user.getStats()))
                .build();
    }

    private static AccountEntity toAccountEntity(Account account) {
        return AccountEntity.builder()
                .accountId(account.getAccountId())
                .registrationId(account.getRegistrationId())
                .password(account.getPassword())
                .loginRole(account.getLoginRole())
                .loginType(account.getLoginType())
                .build();
    }
    private static ProfileEntity toProfileEntity(Profile profile) {
        return ProfileEntity.builder()
                .nickname(profile.getNickname())
                .profileImg(profile.getProfileImg())
                .introduction(profile.getIntroduction())
                .mbti(profile.getMbti())
                .gender(profile.getGender())
                .preferredSizes(profile.getPreferredSizes())
                .careAvailable(profile.getCareAvailable())
                .build();
    }
    private static LocationEntity toLocationEntity(Location location) {
        return LocationEntity.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .region(location.getRegion())
                .regionCode(location.getRegionCode())
                .build();
    }
    private static UserStatsEntity toStatsEntity(UserState userState) {
        return new UserStatsEntity(userState.getRecommendationCount(), userState.getCareCompletionCount());
    }
}
