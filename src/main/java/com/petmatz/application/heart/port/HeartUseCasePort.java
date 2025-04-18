package com.petmatz.application.heart.port;

import com.petmatz.application.heart.dto.HeartedUserInfo;

import java.util.List;

public interface HeartUseCasePort {

    void toggleHeart(Long myId, Long heartId);
    List<HeartedUserInfo> getHeartList(Long myId);

}
