package com.petmatz.domain.user.response;

import com.petmatz.user.common.LogInResponseDto;
import com.petmatz.user.common.ResponseCode;
import com.petmatz.user.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class UpdateLocationResponseDto {
    private String region;
    private Integer regionCode;

    public UpdateLocationResponseDto(String region, Integer regionCode){
        this.region = region;
        this.regionCode=regionCode;
    }
}
