package garbege.service.user.response;

import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import com.petmatz.garbege.service.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class GetOtherProfileResponseDto{
    private Long id;
    private String accountId;
    private String nickname;
    private String profileImg;
    private List<PreferredSize> preferredSize;
    private Gender gender;
    private Boolean isRegistered;
    private Integer recommendationCount;
    private Integer careCompletionCount;
    private Boolean isCareAvailable;
    private String introduction;
    private String mbti;
    private String region;
    private Integer regionCode;
    private boolean isMyHeartUser;

    public GetOtherProfileResponseDto(User user, boolean isMyHeartUser) {
        this.id = user.getId();
        this.accountId = user.getAccountId();
        this.nickname = user.getNickname();
        this.profileImg=user.getProfileImg();
        this.preferredSize = user.getPreferredSizes();
        this.gender = user.getGender();
        this.isRegistered = user.getIsRegistered();
        this.recommendationCount = user.getRecommendationCount();
        this.careCompletionCount = user.getCareCompletionCount();
        this.introduction=user.getIntroduction();
        this.isCareAvailable = user.getCareAvailable();
        this.mbti=user.getMbti();
        this.region = user.getRegion();
        this.isMyHeartUser=isMyHeartUser;
        this.regionCode=user.getRegionCode();
    }

//    public static ResponseEntity<LogInResponseDto> userNotFound() {
//        LogInResponseDto responseBody=new LogInResponseDto(ResponseCode.ID_NOT_FOUND, ResponseMessage.ID_NOT_FOUND);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
//    }
//
//    public static ResponseEntity<LogInResponseDto> success(User user,boolean isMyHeartUser) {
//        GetOtherProfileResponseDto responseBody = new GetOtherProfileResponseDto(user,isMyHeartUser);
//        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
//    }

}
