package com.petmatz.application.user.port;

import com.petmatz.api.user.dto.SignUpResponse;
import com.petmatz.application.user.dto.SignInInfo;
import com.petmatz.application.user.dto.SignUpInfo;

public interface UserUseCasePort {

    SignInInfo signIn(String accountId, String password);
    SignUpResponse signUp(SignUpInfo info);

    void updateLocation(Double latitude, Double longitude);
}
