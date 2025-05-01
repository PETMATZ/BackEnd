package com.petmatz.domain.user.model;

import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Profile {

    private String nickname;
    private String profileImg;
    private String introduction;
    private String mbti;
    private Gender gender;
    private List<PreferredSize> preferredSizes;
    private Boolean careAvailable;


    public void updateProfile(String nickname, List<PreferredSize> preferredSizes, String introduction, boolean careAvailable, String imgURL) {
        this.nickname = nickname;
        this.preferredSizes = preferredSizes;
        this.introduction = introduction;
        this.careAvailable = careAvailable;
        this.profileImg = imgURL;
    }
}
