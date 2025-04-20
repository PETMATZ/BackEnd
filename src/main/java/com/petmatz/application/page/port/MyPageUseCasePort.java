package com.petmatz.application.page.port;

import com.petmatz.application.page.dto.EditMyProfileInfo;
import com.petmatz.application.page.dto.MyPageInfo;
import com.petmatz.application.page.dto.OtherProfileInfo;

public interface MyPageUseCasePort {

    MyPageInfo getMyPage(Long userId);

    OtherProfileInfo getOtherMypage(Long userId, Long myId);

    String editMyProfile(EditMyProfileInfo info, Long userId, String userEmail);

}
