package com.petmatz.application.main_page.port;

import com.petmatz.application.main_page.dto.EditMyProfileInfo;
import com.petmatz.application.main_page.dto.MyPageInfo;
import com.petmatz.application.main_page.dto.OtherProfileInfo;

public interface MyPageUseCasePort {

    MyPageInfo getMyPage(Long userId);

    OtherProfileInfo getOtherMypage(Long userId, Long myId);

    String editMyProfile(EditMyProfileInfo info, Long userId, String userEmail);

}
