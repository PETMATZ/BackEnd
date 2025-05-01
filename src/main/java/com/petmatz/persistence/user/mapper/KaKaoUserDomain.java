package com.petmatz.persistence.user.mapper;

import com.petmatz.domain.user.User;
import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import com.petmatz.domain.user.model.Account;
import com.petmatz.domain.user.model.Profile;
import com.petmatz.domain.user.model.UserState;

public class KaKaoUserDomain {

    public static User createUser(String kakaoAccountId, String email, String nickname, String profileImage) {
        return User.builder()
                .account(createAccount(email, kakaoAccountId))
                .profile(createProfile(nickname, profileImage))
                .stats(createUserState())
                .build();
    }

    private static Account createAccount(String email, String kakaoAccountId) {
        return Account.builder()
                .accountId(email)
                .registrationId(kakaoAccountId)
                .password("password") // 보안 강화 필요
                .loginType(LoginType.KAKAO)
                .loginRole(LoginRole.ROLE_USER)
                .build();
    }

    private static Profile createProfile(String nickname, String profileImage) {
        return Profile.builder()
                .nickname(nickname)
                .profileImg(profileImage)
                .build();
    }

    private static UserState createUserState() {
        return UserState.builder()
                .recommendationCount(0)
                .careCompletionCount(0)
                .build();
    }



}
