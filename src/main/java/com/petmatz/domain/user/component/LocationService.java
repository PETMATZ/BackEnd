package com.petmatz.domain.user.component;

import com.petmatz.common.security.utils.JwtExtractProvider;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.info.UpdateLocationInfo;
import com.petmatz.domain.user.repository.UserRepository;
import com.petmatz.domain.user.response.UpdateLocationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final JwtExtractProvider jwtExtractProvider;
    private final GeocodingService geocodingService;
    private final UserUtils userUtils;

    @Transactional
    public UpdateLocationResponseDto updateLocation(UpdateLocationInfo info) {
            Long userId = jwtExtractProvider.findIdFromJwt();
            User user = userUtils.findIdUser(userId);

            /**
             * 현재는 static 클래스라 이렇게 접근
             */
            GeocodingService.KakaoRegion kakaoRegion = geocodingService.getValidRegion(info.getLatitude(), info.getLongitude());
            user.updateLocation(info, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());

            return new UpdateLocationResponseDto(kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());
    }
}
