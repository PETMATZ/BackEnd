package com.petmatz.domain.old.match.component;


import com.petmatz.domain.old.match.exception.MatchException;
import org.springframework.stereotype.Service;

import static com.petmatz.domain.old.match.exception.MatchErrorCode.INSUFFICIENT_CARE_DATA;

@Service
public class MatchCareCalculator {

    public double calculateCareScore(Boolean isCareAvailable) {
        if (isCareAvailable == null) {
            throw new MatchException(INSUFFICIENT_CARE_DATA);
        }
        if (isCareAvailable) {
            return 15.0;
        } else {
            return 7.0;
        }
    }
}
