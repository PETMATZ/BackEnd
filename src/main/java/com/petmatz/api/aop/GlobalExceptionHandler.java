package com.petmatz.api.aop;

import com.petmatz.api.global.dto.Response;
import com.petmatz.application.user.exception.UserException;
import com.petmatz.common.exception.BaseErrorCode;
import com.petmatz.common.exception.ErrorReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Response<Void>> handleUserException(UserException ex) {
        // 예외에서 에러 코드 가져오기
        BaseErrorCode baseErrorCode = ex.getErrorCode();
        ErrorReason errorReason = baseErrorCode.getErrorReason();

        int statusCode = errorReason.status();
        String message = errorReason.message();
        String errorCode2 = errorReason.errorCode();

        // 실패 응답 생성
        Response<Void> response = Response.error(errorCode2, message);

        // 상태 코드와 함께 응답 반환
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }
}
