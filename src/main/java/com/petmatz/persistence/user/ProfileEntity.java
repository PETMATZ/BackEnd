package com.petmatz.persistence.user;

import com.petmatz.domain.user.constant.Gender;
import com.petmatz.persistence.user.utils.PreferredSizeConverter;
import com.petmatz.domain.user.constant.PreferredSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Embeddable
@RequiredArgsConstructor
@SuperBuilder
public class ProfileEntity {

    private String nickname;

    private String profileImg;

    private String introduction;

    private String mbti;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Convert(converter = PreferredSizeConverter.class)
    @Column(name = "preferred_size")
    private List<PreferredSize> preferredSizes;

    @Column(name = "is_care_available")
    private Boolean careAvailable;

    public void updateProfile(String nickname, List<PreferredSize> preferredSizes, String introduction, boolean careAvailable, String imgURL) {
        this.nickname = nickname;
        this.preferredSizes = preferredSizes;
        this.introduction = introduction;
        this.careAvailable = careAvailable;
        this.profileImg = imgURL;
    }
}