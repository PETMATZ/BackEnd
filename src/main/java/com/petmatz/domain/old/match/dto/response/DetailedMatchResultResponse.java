package com.petmatz.domain.old.match.dto.response;

public record DetailedMatchResultResponse(
        Long id,
        String nickname,
        String profileImg,
        Integer recommendationCount,
        String region,
        Integer careCompletionCount,
        String accountId
        // double distance,

) {}
