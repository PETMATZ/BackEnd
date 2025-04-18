package com.petmatz.persistence.user;

import com.petmatz.domain.user.constant.LoginRole;
import com.petmatz.domain.user.constant.LoginType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Embeddable
@RequiredArgsConstructor
@SuperBuilder
public class AccountEntity {

    @Column(name = "accountId")
    private String accountId;

    @Column(name = "registrationId")
    private String registrationId;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_role")
    private LoginRole loginRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type")
    private LoginType loginType;

}
