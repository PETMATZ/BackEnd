package com.petmatz.domain.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserState {
    private Integer recommendationCount;
    private Integer careCompletionCount;

}
