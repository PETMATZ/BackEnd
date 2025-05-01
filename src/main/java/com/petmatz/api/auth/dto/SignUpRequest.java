package com.petmatz.api.auth.dto;


import com.petmatz.application.user.dto.SignUpInfo;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Schema(description = "이메일", example = "test@gmail.com")
    private final String accountId; // account_id와 매핑

    @NotBlank
    @Schema(description = "비밀번호", example = "123456")
    private final String password;

    @NotBlank
    @Schema(description = "", example = "")
    private final String certificationNumber;

    @NotBlank
    @Schema(description = "닉네임", example = "호돌이")
    private final String nickname;

    @Schema(description = "프로필 사진 주소", example = "")
    private final String profileImg;

    @NotBlank
    @Schema(description = "성별", example = "MALE")
    private final Gender gender;

    @NotBlank
    @Schema(description = "선호 사이즈", example = "SMALL")
    private final List<PreferredSize> preferredSizes; // 여러 값을 허용하도록 변경

    @NotBlank
    @Schema(description = "돌봄 여부", example = "true")
    private final Boolean isCareAvailable;

    @Schema(description = "소개글", example = "잘부탁드립니다.")
    private final String introduction; //선택이므로 @NotBlank 제외

    @NotBlank
    @Schema(description = "mbti", example = "ENFP")
    private final String mbti;

    @Schema(description = "위도", example = "37.5665")
    private final Double latitude;

    @Schema(description = "경도", example = "126.9780")
    private final Double longitude;

    public static SignUpInfo of(SignUpRequest reqDto) {
        return SignUpInfo.builder()
                .accountId(reqDto.getAccountId())
                .password(reqDto.getPassword())
                .certificationNumber(reqDto.getCertificationNumber())
                .nickname(reqDto.getNickname())
                .profileImg(reqDto.getProfileImg())
                .gender(reqDto.getGender())
                .preferredSizes(reqDto.getPreferredSizes())
                .isCareAvailable(reqDto.getIsCareAvailable())
                .introduction(reqDto.getIntroduction())
                .mbti(reqDto.getMbti())
                .latitude(reqDto.getLatitude())
                .longitude(reqDto.getLongitude())
                .build();
    }
}