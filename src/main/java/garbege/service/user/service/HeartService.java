//package garbege.service.user.service;
//
//import garbege.api.user.request1.HeartedUserDto;
//import garbege.api.user.request1.HeartingRequestDto;
//import garbege.service.user.component.HeartComponent;
//import garbege.service.user.provider.UserUtils;
//import com.petmatz.garbege.service.user.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class HeartService {
//
//    private final JwtExtractProvider jwtExtractProvider;
//    private final HeartComponent heartComponent;
//    private final UserUtils userUtils;
//
//
//    @Transactional
//    public void hearting(HeartingRequestDto dto) {
//        Long heartedId = dto.getHeartedId();
//        Long userId = jwtExtractProvider.findIdFromJwt();
//
//        heartComponent.validateHeartUser(heartedId);
//        User currentUser = userUtils.getCurrentUser(userId);
//        heartComponent.toggleHeart(currentUser.getId(), heartedId);
//    }
//
//
//    public List<HeartedUserDto> getHeartedList() {
//        Long userId = jwtExtractProvider.findIdFromJwt();
//        return heartComponent.getHeartedUsers(userId);
//    }
//}
