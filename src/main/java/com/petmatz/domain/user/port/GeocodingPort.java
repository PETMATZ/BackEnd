package com.petmatz.domain.user.port;

import com.petmatz.application.user.dto.RegionInfo;

public interface GeocodingPort {
    RegionInfo getRegion(double latitude, double longitude);
}
