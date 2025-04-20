package com.petmatz.api.user.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class EditMyProfileResponse {
    private String resultImgURL;

    public EditMyProfileResponse(String resultImgURL){
        this.resultImgURL=resultImgURL;
    }


}
