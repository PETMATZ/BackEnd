package com.petmatz.domain.user.port;

import com.petmatz.domain.user.User;

public interface UserQueryPort {

    boolean existsByAccountId(String accountId);

    User findByUserInfo(String accountId);

    String findAccountIdByUserId(Long id);

    User findById(Long userId);


}
