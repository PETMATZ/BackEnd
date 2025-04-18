package garbege.service.user.service;

import garbege.service.user.response.RankUserResponse;

import java.util.List;

public interface RankService {
    List<RankUserResponse> getTopRankings();

    List<RankUserResponse> getTopRankingsByRegion();

}
