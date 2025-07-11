package com.petmatz.persistence.global;

import lombok.Builder;

@Builder
public record S3ImgDataInfo(

        Long UUID,

        String resultImgURL

) {
    public static S3ImgDataInfo of(Long id, String resultImgURL) {
        return S3ImgDataInfo.builder()
                .UUID(id)
                .resultImgURL(resultImgURL)
                .build();
    }
}
