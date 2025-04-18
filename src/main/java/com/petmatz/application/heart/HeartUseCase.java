package com.petmatz.application.heart;

import com.petmatz.application.heart.dto.HeartedUserInfo;
import com.petmatz.application.heart.port.HeartUseCasePort;
import com.petmatz.domain.heart.Heart;
import com.petmatz.domain.heart.port.HeartCommendPort;
import com.petmatz.domain.heart.port.HeartQueryPort;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HeartUseCase implements HeartUseCasePort {

    private final HeartQueryPort heartQueryPort;
    private final HeartCommendPort heartCommendPort;
    private final UserQueryPort userQueryPort;

    @Override
    public void toggleHeart(Long myId, Long heartId) {
        heartCommendPort.toggleHeart(myId, heartId);
    }

    @Override
    public List<HeartedUserInfo> getHeartList(Long myId) {
        List<Heart> heartList = heartQueryPort.getHeartedUsers(myId);

        return heartList.stream()
                .map(heart -> {
                    User heartedUser = userQueryPort.findById(heart.getHeartedId());
                    return new HeartedUserInfo(
                            heart.getMyId(),
                            heart.getHeartedId(),
                            heartedUser.getProfile().getNickname(),
                            heartedUser.getProfile().getProfileImg(),
                            heartedUser.getProfile().getCareAvailable(),
                            heartedUser.getProfile().getPreferredSizes()
                    );
                })
                .toList();
    }
}





