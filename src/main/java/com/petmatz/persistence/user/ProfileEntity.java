package com.petmatz.persistence.user;

import com.petmatz.domain.pet.PetGender;
import com.petmatz.domain.user.constant.Gender;
import garbege.service.user.entity.PreferredSizeConverter;
import com.petmatz.domain.user.constant.PreferredSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Embeddable
@RequiredArgsConstructor
@SuperBuilder
public class ProfileEntity {

    private String nickname;

    private String profileImg;

    private String introduction;

    private String mbti;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Convert(converter = PreferredSizeConverter.class)
    @Column(name = "preferred_size")
    private List<PreferredSize> preferredSizes;

    @Column(name = "is_care_available")
    private Boolean careAvailable;
}