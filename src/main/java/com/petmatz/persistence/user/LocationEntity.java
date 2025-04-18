package com.petmatz.persistence.user;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@Embeddable
@RequiredArgsConstructor
@SuperBuilder
public class LocationEntity {

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "region")
    private String region;

    @Column(name = "region_code")
    private Integer regionCode;

    public void updateLocation(Double latitude,Double longitude,String region,Integer regionCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.regionCode = regionCode;
    }

}
