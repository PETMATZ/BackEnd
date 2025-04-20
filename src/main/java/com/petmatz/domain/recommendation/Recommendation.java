package com.petmatz.domain.recommendation;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Recommendation {
    private final Long id;
    private final Long myId;
    private final Long recommendedId;
}
