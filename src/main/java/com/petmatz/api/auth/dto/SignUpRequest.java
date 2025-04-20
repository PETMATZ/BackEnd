package com.petmatz.api.auth.dto;


import com.petmatz.application.user.dto.SignUpInfo;
import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
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
    private final String accountId; // account_id와 매핑

    @NotBlank
    private final String password;

    @NotBlank
    private final String certificationNumber;

    @NotBlank
    private final String nickname;

    private final String profileImg;

    @NotBlank
    private final Gender gender;

    @NotBlank
    private final List<PreferredSize> preferredSizes; // 여러 값을 허용하도록 변경

    @NotBlank
    private final Boolean isCareAvailable;

    private final String introduction; //선택이므로 @NotBlank 제외

    @NotBlank
    private final String mbti;

    private final Double latitude;

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