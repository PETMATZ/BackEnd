package com.petmatz.domain.recommendation;

import lombok.Builder;

@Builder
public class Recommendation {
    private final Long id;
    private final Long myId;
    private final Long recommendedId;

}
