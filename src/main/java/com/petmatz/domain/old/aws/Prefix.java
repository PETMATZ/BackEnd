package com.petmatz.domain.old.aws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Prefix {

    CUSTOM_USER_IMG("유저이미지_폴더"),
    PET_IMG("펫이미지_폴더"),
    CARE_HISTORY_IMG("돌봄내역_폴더");

    private final String koreaName;

    public static String returnKoreaName(String name) {
        for (Prefix value : Prefix.values()) {
            if (value.name().equals(name)) {
                return value.koreaName;
            }
        }
        //TODO 에러 발생 시키기
        return "없음";
    }

}
