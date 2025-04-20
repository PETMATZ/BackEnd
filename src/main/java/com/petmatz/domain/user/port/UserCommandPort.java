package com.petmatz.domain.user.port;

import com.petmatz.domain.user.User;

public interface UserCommandPort {

    Long save(User user);
    void updateUserLocation(Double latitude, Double longitude, String region, Integer regionCode, String accountId);
    void updateUser(User user, Long userId);
    void updatePassword(String currentPassword, String newPassword, Long userId);
    void updateRecommendation(Long recommendedId);
    void deleteUser(Long userId);

}
