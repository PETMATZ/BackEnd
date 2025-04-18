//package garbege.service.user.service;
//
//import com.petmatz.common.security.jwt.JwtExtractProvider;
//import garbege.service.user.provider.UserUtils;
//import com.petmatz.infra.adapter.kakao.dto.KakaoRegion;
//import com.petmatz.garbege.service.user.User;
//import garbege.service.user.info.UpdateLocationInfo;
//import garbege.service.user.response.UpdateLocationResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class LocationService {
//
//    private final JwtExtractProvider jwtExtractProvider;
//    private final GeocodingComponent geocodingComponent;
//    private final UserUtils userUtils;
//
//    @Transactional
//    public UpdateLocationResponseDto updateLocation(UpdateLocationInfo info) {
//            Long userId = jwtExtractProvider.findIdFromJwt();
//            User user = userUtils.findIdUser(userId);
//
//            KakaoRegion kakaoRegion = geocodingComponent.getValidRegion(info.getLatitude(), info.getLongitude());
//            user.updateLocation(info, kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());
//
//            return new UpdateLocationResponseDto(kakaoRegion.getRegionName(), kakaoRegion.getCodeAsInteger());
//    }
//}
