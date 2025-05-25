package com.petmatz.domain.user.exception;

import com.petmatz.common.exception.BaseErrorCode;
import com.petmatz.common.exception.ErrorReason;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    INSUFFICIENT_LOCATION_DATA(404, "INSUFFICIENT_LOCATION_DATA", "유요한 위치 정보를 가져올 수 없습니다."),
    MISS_KAKAO_LOACTION(400, "MISS_KAKAO_LOACTION", "카카오 지역 api를 호출 할 수 없습니다."),
    INSUFFICIENT_LATITUDE_DATA(400, "INSUFFICIENT_LATITUDE_DATA", "위도가 없습니다."),
    INSUFFICIENT_LONGITUDE_DATA(400, "INSUFFICIENT_LONGITUDE_DATA", "경도가 없습니다."),
    INVALID_MATCH_DATA(400, "INVALID_MATCH_DATA", "위도 또는 경도가 누락되었습니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "사용자가 존재하지 않습니다."),
    JWT_USER_NOT_FOUND(404, "JWT_USER_NOT_FOUND", "토큰에서 추출한 사용자가 존재하지 않습니다."),
    CERTIFICATION_EXPIRED(400, "CERTIFICATION_EXPIRED", "인증 시간이 만료되었습니다."),
    MISS_MATCH_CODE(400, "MISS_MATCH_CODE", "인증 번호가 일치하지 않습니다."),
    USER_DUPLICATE(400, "USER_DUPLICATE", "중복된 사용자가 있습니다."),
    HEART_USER_NOT_FOUND(404, "HEART_USER_NOT_FOUND", "찜한 사용자를 찾을 수 없습니다."),
    FAIL_MAIL_SEND(400, "FAIL_MAIL_SEND", "메일 전송에 실패하였습니다."),
    HEART_USER_DUPLICATE(400, "HEART_USER_DUPLICATE", "찜한 사용자가 이미 존재합니다."),
    PASS_WORD_MISMATCH(401, "PASSWORD_MISMATCH", "비밀번호 불일치");


    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }



    UserErrorCode(int status, String errorCode, String message) {
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

