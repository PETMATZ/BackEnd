package com.petmatz.domain.user.response;

import lombok.Getter;


public record SignUpResponse(
        Long id,
        String imgURL) {
}
