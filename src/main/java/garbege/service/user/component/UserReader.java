package garbege.service.user.component;

import com.petmatz.garbege.service.user.User;
import com.petmatz.persistence.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    /**
     * 이것도 utils를 만들어서 곧 지울듯
     * 종원님 이거 다 쓰시면 지워주세욥
     */
    private final UserRepository userRepository;

    public User getAuthenticatedUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found :" + userId));
    }
}
