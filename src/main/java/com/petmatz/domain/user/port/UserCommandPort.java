package com.petmatz.domain.user.port;

import com.petmatz.domain.user.User;

public interface UserCommandPort {

    Long save(User user);

    void updateUserLocation(Double latitude, Double longitude, String region, Integer regionCode, String accountId);

}
