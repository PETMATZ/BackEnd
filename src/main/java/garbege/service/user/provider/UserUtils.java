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

    public User findIdUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        return user;
    }

    public String findAccountIdByUserId(Long userId) {
        return userRepository.findUsernameById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }


    public void findJwtUser(Long userId) {
        if (userId == null) {
            throw new UserException(JWT_USER_NOT_FOUND);
        }
    }

}
