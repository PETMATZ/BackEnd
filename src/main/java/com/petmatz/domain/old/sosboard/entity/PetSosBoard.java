//package com.petmatz.domain.old.sosboard.entity;
//
//import com.petmatz.persistence.global.BaseEntity;
//import com.petmatz.domain.pet.entity.Pet;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//
////Pet, SosBoard 중간테이블
//@Entity
//@Table(name = "pet_sos_board")
//@Getter
//@SuperBuilder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PetSosBoard extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pet_id", nullable = false)
//    private Pet pet;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sos_board_id", nullable = false)
//    private SosBoard sosBoard;
//
//
//    public void addSosBoard(SosBoard board) {
//        sosBoard = board;
//    }
//}
