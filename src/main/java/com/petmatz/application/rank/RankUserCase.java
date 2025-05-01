package com.petmatz.application.rank;

import com.petmatz.application.rank.port.RankUserCasePort;
import com.petmatz.domain.user.User;
import com.petmatz.domain.user.port.UserQueryPort;
import garbege.service.user.response.RankUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//TODO Rank 관련 정보는 Redis에 저장해야 됨, 리펙토링 필수
public class RankUserCase implements RankUserCasePort {

    private final UserQueryPort userQueryPort;

    @Override
    public List<RankUserResponse> getTopRankingsByRegion(Long userId) {

        User currentUser = userQueryPort.findById(userId);
        Integer regionCode = currentUser.getLocation().getRegionCode();

        List<User> usersByRegion = userQueryPort.findByRegionCodeOrderByRecommendationCountDesc(regionCode);

        return usersByRegion.stream()
                .limit(10)
                .map(user -> {
                    long rank = usersByRegion.indexOf(user) + 1;
                    String profileImg;
                    if (rank <= 3) {
                        profileImg = user.getProfile().getProfileImg(); // 1~3등만 프로필 사진 포함
                    } else {
                        profileImg = null; // 4등부터는 프로필 사진 제외
                    }
                    return new RankUserResponse(
                            user.getId(),
                            rank,
                            user.getProfile().getNickname(),
                            user.getStats().getRecommendationCount(),
                            profileImg
                    );
                })
                .collect(Collectors.toList());
    }
}
