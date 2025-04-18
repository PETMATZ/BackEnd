package com.petmatz.persistence.user;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Embeddable
@RequiredArgsConstructor
public class UserStatsEntity {

    private Integer recommendationCount;

    private Integer careCompletionCount;

    public UserStatsEntity(Integer recommendationCount, Integer careCompletionCount) {
        this.recommendationCount = recommendationCount;
        this.careCompletionCount = careCompletionCount;
    }
}
