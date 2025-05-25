//package garbege.service.user.component;
//
//import com.petmatz.persistence.heart.HeartEntity;
//import garbege.api.user.request1.HeartedUserDto;
//import com.petmatz.domain.user.utils.UserFactory;
//import com.petmatz.domain.user.exception.UserException;
//import com.petmatz.persistence.heart.repository.HeartRepository;
//import com.petmatz.persistence.user.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.petmatz.domain.user.exception.UserErrorCode.HEART_USER_NOT_FOUND;
//
//@RequiredArgsConstructor
//@Component
//public class HeartComponent {
//
//    private final UserRepository userRepository;
//    private final HeartRepository heartRepository;
//
//    @Transactional
//    public void validateHeartUser(Long heartId) {
//        if (!userRepository.existsById(heartId)) {
//            throw new UserException(HEART_USER_NOT_FOUND);
//        }
//    }
//
//    @Transactional
//    public void toggleHeart(Long myId, Long heartId) {
//        Optional<HeartEntity> existingHeart = heartRepository.findByMyIdAndHeartedId(myId, heartId);
//
//        if (existingHeart.isPresent()) {
//            heartRepository.delete(existingHeart.get());
//            return; // 혜제
//        }
//
//        Heart heart = UserFactory.createHeart(myId, heartId);
//        heartRepository.save(heart);
//    }
//
//    @Transactional
//    public List<HeartedUserDto> getHeartedUsers(Long myId) {
//
//        List<Heart> heartList = heartRepository.findAllByMyId(myId);
//
//        return heartList.stream()
//                .map(heart -> {
//                    User heartedUser = userRepository.findById(heart.getHeartedId())
//                            .orElseThrow(() -> new UserException(HEART_USER_NOT_FOUND));
//                    return new HeartedUserDto(
//                            heart.getMyId(),
//                            heart.getHeartedId(),
//                            heartedUser.getNickname(),
//                            heartedUser.getProfileImg(),
//                            heartedUser.getCareAvailable(),
//                            heartedUser.getPreferredSizes()
//                    );
//                })
//                .toList();
//    }
//}
