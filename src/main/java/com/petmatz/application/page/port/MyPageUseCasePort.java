package com.petmatz.application.page.port;

import com.petmatz.application.page.dto.MyPageInfo;

public interface MyPageUseCasePort {

    MyPageInfo getMyPage(Long userId);

}
