package com.petmatz.common.exception;

import lombok.Getter;

@Getter
public class PersistenceException extends CustomException {

    public PersistenceException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public PersistenceException(BaseErrorCode errorCode) {
        super(errorCode, "DB 계층 예외");
    }
}
