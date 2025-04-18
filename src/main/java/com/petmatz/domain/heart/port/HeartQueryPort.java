package com.petmatz.domain.heart.port;

import com.petmatz.domain.heart.Heart;

import java.util.List;

public interface HeartQueryPort {

    List<Heart> getHeartedUsers(Long myId);

}
