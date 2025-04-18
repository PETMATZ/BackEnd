package garbege.service.user.provider;

import com.petmatz.garbege.service.user.User;
import com.petmatz.application.user.exception.UserException;
import com.petmatz.persistence.heart.repository.HeartRepository;
import com.petmatz.persistence.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.petmatz.application.user.exception.UserErrorCode.*;

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
    public User findUser(Object inAccountId) {
        String accountId = String.valueOf(inAccountId);
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
        return userRepository.findUsernameById(userId)
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
