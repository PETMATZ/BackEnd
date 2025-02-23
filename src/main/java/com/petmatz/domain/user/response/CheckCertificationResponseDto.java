package com.petmatz.domain.user.response;

import com.petmatz.user.common.LogInResponseDto;
import com.petmatz.user.common.ResponseCode;
import com.petmatz.user.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CheckCertificationResponseDto extends LogInResponseDto {
    private CheckCertificationResponseDto() {
        super();
    }


    public static ResponseEntity<LogInResponseDto> certificationFail() {
        LogInResponseDto responseBody = new LogInResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    public static ResponseEntity<LogInResponseDto> certificationExpired() {
        LogInResponseDto responseBody = new LogInResponseDto(ResponseCode.CERTIFICATION_EXPIRED, ResponseMessage.CERTIFICATION_EXPIRED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
