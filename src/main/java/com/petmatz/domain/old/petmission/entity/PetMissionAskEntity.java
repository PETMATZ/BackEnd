//package com.petmatz.domain.old.petmission.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class PetMissionAskEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String comment;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pet_mission_id", nullable = false) // 외래 키
//    private PetMissionEntity petMission;
//
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JoinColumn(name = "pet_mission_answer_id")
//    private PetMissionAnswerEntity missionAnswer;
//
//    @Builder
//    public PetMissionAskEntity(String comment, PetMissionEntity petMission, PetMissionAnswerEntity missionAnswer) {
//        this.comment = comment;
//        this.petMission = petMission;
//        this.missionAnswer = missionAnswer;
//    }
//    public static PetMissionAskEntity of(String comment) {
//        return PetMissionAskEntity.builder()
//                .comment(comment)
//                .build();
//    }
//
//    public void addPetMission(PetMissionEntity petMission) {
//        this.petMission = petMission;
//    }
//
//    public void addPetMissionAnswer(PetMissionAnswerEntity petMissionAnswerEntity) {
//        missionAnswer = petMissionAnswerEntity;
//    }
//}
