package com.petmatz.domain.user.response;

import com.petmatz.user.common.LogInResponseDto;
import com.petmatz.user.common.ResponseCode;
import com.petmatz.user.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetRecommendationResponseDto{

    private boolean isRecommended;
    public GetRecommendationResponseDto(boolean isRecommended) {
        this.isRecommended=isRecommended;
    }

    public static ResponseEntity<LogInResponseDto> userNotFound() {
        LogInResponseDto responseBody=new LogInResponseDto(ResponseCode.ID_NOT_FOUND, ResponseMessage.ID_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    public static ResponseEntity<GetRecommendationResponseDto> success(boolean isRecommended) {
        GetRecommendationResponseDto responseBody = new GetRecommendationResponseDto(isRecommended);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
