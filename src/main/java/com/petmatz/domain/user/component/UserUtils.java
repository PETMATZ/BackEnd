package com.petmatz.domain.user.component;

import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.exception.UserException;
import com.petmatz.domain.user.repository.HeartRepository;
import com.petmatz.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.petmatz.domain.user.exception.UserErrorCode.*;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    public void checkDuplicateAccountId(String accountId) {
        if (userRepository.existsByAccountId(accountId)) {
            throw new UserException(USER_DUPLICATE);
        }
    }

    public void checkDuplicateId(Long userId) {
        if (userRepository.existsById(userId)) {
            throw new UserException(USER_DUPLICATE);
        }
    }
    public User findUser(String accountId) {
        User user = userRepository.findByAccountId(accountId);
        if (user == null) {
            throw new UserException(USER_NOT_FOUND);
        }
        return user;
    }

    public User findIdUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        return user;
    }

    public String findAccountIdByUserId(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }


    @Transactional
    public User getCurrentUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }

    public void findJwtUser(Long userId) {
        if (userId == null) {
            throw new UserException(JWT_USER_NOT_FOUND);
        }
    }

    public boolean checkHeart(Long myId, Long userId) {
        if (heartRepository.existsByMyIdAndHeartedId(myId, userId)) {
            return false;
        }
        return true;
    }
}
