package com.petmatz.domain.user.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EditMyProfileResponseDto {
    private String resultImgURL;

    public EditMyProfileResponseDto(String resultImgURL){
        this.resultImgURL=resultImgURL;
    }

    public static ResponseEntity<EditMyProfileResponseDto> success(String resultImgURL) { // 반환 타입 수정
        EditMyProfileResponseDto responseBody = new EditMyProfileResponseDto(resultImgURL);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
