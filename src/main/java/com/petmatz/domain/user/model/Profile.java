package com.petmatz.domain.user.model;

import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class Profile {

    private String nickname;
    private String profileImg;
    private String introduction;
    private String mbti;
    private Gender gender;
    private List<PreferredSize> preferredSizes;
    private Boolean careAvailable;

}
