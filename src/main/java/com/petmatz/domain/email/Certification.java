package com.petmatz.domain.email;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Certification {

    private final Long id;
    private final String accountId;
    private final String certificationNumber;
    private final Boolean isVerified;


}
