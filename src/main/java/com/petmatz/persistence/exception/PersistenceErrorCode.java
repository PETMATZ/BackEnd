package com.petmatz.persistence.exception;

import com.petmatz.common.exception.BaseErrorCode;
import com.petmatz.common.exception.ErrorReason;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PersistenceErrorCode implements BaseErrorCode {

    USER_NOT_FOUND(404, "PERSISTENCE_ERROR_404", "유저 정보를 찾을 수 없습니다.")
    ;



    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }



    PersistenceErrorCode(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int status() {
        return status;
    }

    public String message() {
        return message;
    }
}

