package com.petmatz.domain.user.response;

import lombok.Getter;

@Getter
public class UpdateLocationResponseDto {
    private String region;
    private Integer regionCode;

    public UpdateLocationResponseDto(String region, Integer regionCode){
        this.region = region;
        this.regionCode=regionCode;
    }
}
