package com.petmatz.domain.heart;

import lombok.Getter;

@Getter
public class Heart {
    private final Long id;
    private final Long myId;
    private final Long heartedId;

    public Heart(Long id, Long myId, Long heartedId) {
        this.id = id;
        this.myId = myId;
        this.heartedId = heartedId;
    }
}
