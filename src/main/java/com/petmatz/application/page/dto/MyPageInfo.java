package com.petmatz.application.page.dto;

import com.petmatz.domain.user.constant.Gender;
import com.petmatz.domain.user.constant.PreferredSize;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class MyPageInfo {

    private Long id;
    private String accountId;
    private String nickname;
    private String profileImg;
    private List<PreferredSize> preferredSizes; // 변경: List로 수정
    private Gender gender;
    private String introduction;
    private Integer recommendationCount;
    private Integer careCompletionCount;
    private Boolean isCareAvailable;
    private String mbti;
    private String region;
    private Integer regionCode;

}
