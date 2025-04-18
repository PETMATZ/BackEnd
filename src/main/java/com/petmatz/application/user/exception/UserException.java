package com.petmatz.application.user.exception;

import com.petmatz.common.exception.BaseErrorCode;
import com.petmatz.common.exception.DomainException;

public class UserException extends DomainException {
    public UserException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
