package com.petmatz.persistence.user;

import com.petmatz.persistence.caht.entity.UserToChatRoomEntity;
import com.petmatz.persistence.global.BaseEntity;
import com.petmatz.domain.old.petmission.entity.UserToPetMissionEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "User")
@Table(name = "User")
public class UserEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AccountEntity accountEntity;

    @Embedded
    private ProfileEntity profileEntity;

    @Embedded
    private LocationEntity locationEntity;

    @Embedded
    private UserStatsEntity userStatsEntity;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserToChatRoomEntity> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserToPetMissionEntity> userPetMissions = new ArrayList<>();

}
