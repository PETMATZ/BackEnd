package com.petmatz.application.heart.dto;

import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class HeartedUserInfo {
    private Long myId;
    private Long heartedId;
    private String nickname;
    private String profileImg;
    private Boolean careAvailable;
    private List<PreferredSize> preferredSizes;

    public HeartedUserInfo(Long myId, Long heartedId, String nickname, String profileImg, Boolean careAvailable, List<PreferredSize> preferredSizes) {
        this.myId = myId;
        this.heartedId = heartedId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.careAvailable = careAvailable;
        this.preferredSizes = preferredSizes;
    }
}
