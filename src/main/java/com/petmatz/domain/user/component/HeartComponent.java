package com.petmatz.domain.user.component;

import com.petmatz.api.user.request.HeartedUserDto;
import com.petmatz.domain.user.entity.Heart;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.entity.UserFactory;
import com.petmatz.domain.user.exception.UserException;
import com.petmatz.domain.user.repository.HeartRepository;
import com.petmatz.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.petmatz.domain.user.exception.UserErrorCode.*;

@RequiredArgsConstructor
@Component
public class HeartComponent {

    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public void validateHeartUser(Long heartId) {
        if (!userRepository.existsById(heartId)) {
            throw new UserException(HEART_USER_NOT_FOUND);
        }
    }

    @Transactional
    public boolean toggleHeart(Long myId, Long heartId) {
        Optional<Heart> existingHeart = heartRepository.findByMyIdAndHeartedId(myId, heartId);

        if (existingHeart.isPresent()) {
            heartRepository.delete(existingHeart.get());
            return false; // 혜제
        }

        Heart heart = UserFactory.createHeart(myId, heartId);
        heartRepository.save(heart);
        return true;
    }

    @Transactional
    public List<HeartedUserDto> getHeartedUsers(Long myId) {

        List<Heart> heartList = heartRepository.findAllByMyId(myId);

        return heartList.stream()
                .map(heart -> {
                    User heartedUser = userRepository.findById(heart.getHeartedId())
                            .orElseThrow(() -> new UserException(HEART_USER_NOT_FOUND));

                    return new HeartedUserDto(
                            heart.getMyId(),
                            heart.getHeartedId(),
                            heartedUser.getNickname(),
                            heartedUser.getProfileImg(),
                            heartedUser.getCareAvailable(),
                            heartedUser.getPreferredSizes()
                    );
                })
                .toList();
    }
}
