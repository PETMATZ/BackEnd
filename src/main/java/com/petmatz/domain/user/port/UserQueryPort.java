package com.petmatz.domain.user.port;

import com.petmatz.domain.user.User;

import java.util.List;

public interface UserQueryPort {

    boolean existsByAccountId(String accountId);

    User findAccountIdByUserInfo(String accountId);

    String findAccountIdByUserId(Long id);

    User findById(Long userId);

    List<User> findByRegionCodeOrderByRecommendationCountDesc(Integer regionCode);

}
