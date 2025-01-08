package com.petmatz.domain.user.component;

import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.exception.UserException;
import com.petmatz.domain.user.info.UpdateLocationInfo;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.UpdateLocationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.petmatz.domain.user.exception.MatchErrorCode.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class LocationComponent {

    private final UserRepository userRepository;
    private final GeocodingService geocodingService;
    private final UserUtils userUtils;

 // 아마 지울듯?
}
