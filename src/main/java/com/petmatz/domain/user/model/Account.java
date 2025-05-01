package com.petmatz.domain.user.model;

import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Account {

    private final String accountId;
    private final String registrationId;
    private final String password;
    private final LoginRole loginRole;
    private final LoginType loginType;

}
