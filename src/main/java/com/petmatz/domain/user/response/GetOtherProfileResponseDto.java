package com.petmatz.domain.user.response;

import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import com.petmatz.domain.user.constant.Role;
import com.petmatz.domain.user.entity.User;
import com.petmatz.user.common.LogInResponseDto;
import com.petmatz.user.common.ResponseCode;
import com.petmatz.user.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetOtherProfileResponseDto extends LogInResponseDto {
    private Long id;
    private String accountId;
    private String nickname;
    private String profileImg;
    private Role role;
    private List<PreferredSize> preferredSize;
    private Gender gender;
    private Boolean isRegistered;
    private Integer recommendationCount;
    private Integer careCompletionCount;
    private Boolean isCareAvailable;
    private String mbti;
    private String region;

    public GetOtherProfileResponseDto(User user) {
        super();
        this.id = user.getId();
        this.accountId = user.getAccountId();
        this.nickname = user.getNickname();
        this.profileImg=user.getProfileImg();
        this.role = user.getRole();
        this.preferredSize = user.getPreferredSizes();
        this.gender = user.getGender();
        this.isRegistered = user.getIsRegistered();
        this.recommendationCount = user.getRecommendationCount();
        this.careCompletionCount = user.getCareCompletionCount();
        this.isCareAvailable = user.getIsCareAvailable();
        this.mbti=user.getMbti();
        this.region = user.getRegion();
    }

    public static ResponseEntity<LogInResponseDto> userNotFound() {
        LogInResponseDto responseBody=new LogInResponseDto(ResponseCode.ID_NOT_FOUND, ResponseMessage.ID_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    public static ResponseEntity<LogInResponseDto> success(User user) {
        GetOtherProfileResponseDto responseBody = new GetOtherProfileResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}