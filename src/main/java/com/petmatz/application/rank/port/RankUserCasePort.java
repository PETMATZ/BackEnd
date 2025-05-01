package com.petmatz.application.rank.port;

import garbege.service.user.response.RankUserResponse;

import java.util.List;

public interface RankUserCasePort {

    List<RankUserResponse> getTopRankingsByRegion(Long userId);
}
