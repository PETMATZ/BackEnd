package com.petmatz.api.user.dto;

import com.petmatz.application.heart.dto.HeartedUserInfo;
import com.petmatz.domain.user.constant.PreferredSize;

import java.util.List;

public record HeartedListResponse(
        Long myId,
        Long heartedId,
        String nickname,
        String profileImg,
        Boolean careAvailable,
        List<PreferredSize>preferredSizes

) {

    public static HeartedListResponse to(HeartedUserInfo heartedUserInfo) {
        return new HeartedListResponse(
                heartedUserInfo.getMyId(),
                heartedUserInfo.getHeartedId(),
                heartedUserInfo.getNickname(),
                heartedUserInfo.getProfileImg(),
                heartedUserInfo.getCareAvailable(),
                heartedUserInfo.getPreferredSizes());
    }

}
