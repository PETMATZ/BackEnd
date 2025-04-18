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

    private String accountId;
    private String registrationId;
    private String password;
    private LoginRole loginRole;
    private LoginType loginType;

}
