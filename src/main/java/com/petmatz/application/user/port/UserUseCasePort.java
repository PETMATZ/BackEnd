package com.petmatz.application.user.port;

import com.petmatz.api.auth.dto.SignUpResponse;
import com.petmatz.application.user.dto.SignInInfo;
import com.petmatz.application.user.dto.SignUpInfo;
import garbege.service.user.info.UserInfo;

public interface UserUseCasePort {

    SignInInfo signIn(String accountId, String password);
    SignUpResponse signUp(SignUpInfo info);
    void updateLocation(Double latitude, Double longitude);
    void updatePassword(String currentPassword, String newPassword, Long userId);
    void secession(Long userId, String password);
    UserInfo selectUserInfo(String receiverEmail);
    String selectUserAccountId(Long userId);
}
