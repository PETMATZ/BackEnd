package com.petmatz.api.user.dto.request;

import com.petmatz.domain.user.constant.PreferredSize;
import com.petmatz.application.main_page.dto.EditMyProfileInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EditMyProfileRequest {

    @Schema(defaultValue = "닉네임", example = "호돌이")
    private String nickname;

    @Schema(defaultValue = "선호 사이즈", example = "SMALL")
    private List<PreferredSize> preferredSizes;

    @Schema(defaultValue = "간단 소개", example = "큰 강아지를 좋아합니다.")
    private String introduction;

    @Schema(defaultValue = "돌봄 여부", example = "true")
    private boolean careAvailable;

    @Schema(defaultValue = "사용자의 프로필 사진 URL", example = "")
    private String profileImg;

    public static EditMyProfileInfo to(EditMyProfileRequest reqDto) {
        return EditMyProfileInfo.builder()
                .nickname(reqDto.getNickname())
                .preferredSizes(reqDto.getPreferredSizes())
                .introduction(reqDto.getIntroduction())
                .careAvailable(reqDto.isCareAvailable()
                )
                .profileImg(reqDto.getProfileImg())
                .build();
    }
}
