package com.petmatz.domain.user.service;

import com.petmatz.api.user.request.HeartedUserDto;
import com.petmatz.api.user.request.HeartingRequestDto;
import com.petmatz.common.security.jwt.JwtExtractProvider;
import com.petmatz.domain.user.component.HeartComponent;
import com.petmatz.domain.user.component.UserUtils;
import com.petmatz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HeartService {

    private final JwtExtractProvider jwtExtractProvider;
    private final HeartComponent heartComponent;
    private final UserUtils userUtils;


    @Transactional
    public void hearting(HeartingRequestDto dto) {
        Long heartedId = dto.getHeartedId();
        Long userId = jwtExtractProvider.findIdFromJwt();

        heartComponent.validateHeartUser(heartedId);
        User currentUser = userUtils.getCurrentUser(userId);
        heartComponent.toggleHeart(currentUser.getId(), heartedId);
    }


    public List<HeartedUserDto> getHeartedList() {
        Long userId = jwtExtractProvider.findIdFromJwt();
        return heartComponent.getHeartedUsers(userId);
    }
}
