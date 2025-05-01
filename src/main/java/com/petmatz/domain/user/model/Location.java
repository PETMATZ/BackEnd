package com.petmatz.domain.user.model;

import com.petmatz.domain.old.match.exception.MatchException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.petmatz.domain.old.match.exception.MatchErrorCode.INSUFFICIENT_LATITUDE_DATA;
import static com.petmatz.domain.old.match.exception.MatchErrorCode.INSUFFICIENT_LONGITUDE_DATA;

@Getter
@Builder
@RequiredArgsConstructor
public class Location {

    private final Double latitude;
    private final Double longitude;
    private final String region;
    private final Integer regionCode;


    public void checkLatitudeLongitude() {
        if (latitude == null || latitude <= 0.0) {
            throw new MatchException(INSUFFICIENT_LATITUDE_DATA);
        }
        if (longitude == null || longitude <= 0.0) {
            throw new MatchException(INSUFFICIENT_LONGITUDE_DATA);
        }
    }
}
