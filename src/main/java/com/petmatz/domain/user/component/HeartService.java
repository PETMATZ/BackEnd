package com.petmatz.domain.user.component;

import com.petmatz.api.user.request.HeartedUserDto;
import com.petmatz.api.user.request.HeartingRequestDto;
import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.entity.Heart;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.entity.UserFactory;
import com.petmatz.domain.user.repository.HeartRepository;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.GetHeartingListResponseDto;
import com.petmatz.domain.user.response.HeartingResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeartService {

    private final UserRepository userRepository;
    private final JwtExtractProvider jwtExtractProvider;
    private final HeartRepository heartRepository;
    private final HeartComponent heartComponent;


    @Transactional
    public void hearting(HeartingRequestDto dto) {
        Long heartedId = dto.getHeartedId();
        Long userId = jwtExtractProvider.findIdFromJwt();

        heartComponent.validateHeartUser(heartedId);
        User currentUser = heartComponent.getCurrentUser(userId);
        heartComponent.toggleHeart(currentUser.getId(), heartedId);
    }


    public List<HeartedUserDto> getHeartedList() {
        Long userId = jwtExtractProvider.findIdFromJwt();
        List<HeartedUserDto> heartedUsers = heartComponent.getHeartedUsers(userId);
        return heartedUsers;
    }
}
