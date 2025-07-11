//package com.petmatz.domain.old.petmission.entity;
//
//import com.petmatz.common.constants.PetMissionStatusZip;
//import com.petmatz.domain.old.petmission.dto.PetMissionInfo;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PetMissionEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private LocalDateTime petMissionStarted;
//
//    private LocalDateTime petMissionEnd;
//
//    @Enumerated(EnumType.STRING)
//    private PetMissionStatusZip status;
//
//    @OneToMany(mappedBy = "petMission", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PetToPetMissionEntity> petToPetMissions = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "petMission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<PetMissionAskEntity> petMissionAsks = new ArrayList<>();
//
//
//    @Builder
//    public PetMissionEntity(LocalDateTime petMissionStarted, LocalDateTime petMissionEnd, PetMissionStatusZip status, List<PetToPetMissionEntity> petToPetMissions, List<PetMissionAskEntity> petMissionAsks) {
//        this.petMissionStarted = petMissionStarted;
//        this.petMissionEnd = petMissionEnd;
//        this.status = status;
//        this.petToPetMissions = new ArrayList<>();
//        this.petMissionAsks =  new ArrayList<>();
//    }
//
//
//    public static PetMissionEntity of(PetMissionInfo petMissionInfo) {
//        return PetMissionEntity.builder()
//                .petMissionStarted(petMissionInfo.missionStarted())
//                .petMissionEnd(petMissionInfo.missionEnd())
//                .status(PetMissionStatusZip.BEF)
//                .build();
//    }
//
//    public void addPetMissionAsk(List<PetMissionAskEntity> ask) {
//        petMissionAsks.addAll(ask);
//        ask.forEach(petMissionAskEntity -> petMissionAskEntity.addPetMission(this));
//    }
//
//    public void addPetToPetMission(PetToPetMissionEntity pet) {
//        this.petToPetMissions.add(pet);
//    }
//
//    public void updatePetMissionStatusZip(PetMissionStatusZip updateStatus) {
//        status = updateStatus;
//    }
//
//
//}
