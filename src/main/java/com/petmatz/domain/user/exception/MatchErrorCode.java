package com.petmatz.domain.user.exception;

import com.petmatz.common.exception.BaseErrorCode;
import com.petmatz.common.exception.ErrorReason;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MatchErrorCode implements BaseErrorCode {

    INSUFFICIENT_LOCATION_DATA(404, "INSUFFICIENT_LOCATION_DATA", "유요한 위치 정보를 가져올 수 없습니다."),
    INSUFFICIENT_LATITUDE_DATA(400, "INSUFFICIENT_LATITUDE_DATA", "위도가 없습니다."),
    INSUFFICIENT_LONGITUDE_DATA(400, "INSUFFICIENT_LONGITUDE_DATA", "경도가 없습니다."),
    INVALID_MATCH_DATA(400, "INVALID_MATCH_DATA", "위도 또는 경도가 누락되었습니다."),
    NULL_PREFERRED_SIZES(400, "NULL_PREFERRED_SIZES", "선호 크기 목록이 없습니다."),
    NULL_TARGET_SIZE(400, "NULL_TARGET_SIZE", "타겟 크기가 없습니다."),
    NULL_MATCH_DATA(204, "NULL_MATCH_DATA", "매칭 데이터가 없습니다."),
    CERTIFICATION_EXPIRED(400, "CERTIFICATION_EXPIRED", "인증 시간이 만료되었습니다."),
    MISS_MATCH_CODE(400, "MISS_MATCH_CODE", "인증 번호가 일치하지 않습니다.");




    private final Integer status;
    private final String errorCode;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, errorCode, message);
    }
}

