package com.petmatz.domain.old.sosboard.entity;


import com.petmatz.persistence.global.BaseEntity;
import com.petmatz.domain.old.sosboard.PaymentType;
import com.petmatz.domain.old.sosboard.dto.SosBoardCreateInfo;
import com.petmatz.domain.old.sosboard.dto.UpdateSosBoardInfo;
import com.petmatz.domain.old.sosboard.exception.SosBoardErrorCode;
import com.petmatz.domain.old.sosboard.exception.SosBoardServiceException;
import com.petmatz.garbege.service.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sos_board")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SosBoard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "sosBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetSosBoard> petSosBoards = new ArrayList<>();

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType; // 시급/일급/협의 중 선택

    @Column(name = "price")
    private Integer price; // 금액 (시급/일급의 경우 필수, 협의는 null 허용)

    @Column(name = "start_date", nullable = false)
    private String startDate; // yyyy-MM-dd HH:mm 형식

    @Column(name = "end_date", nullable = false)
    private String endDate; // yyyy-MM-dd HH:mm 형식

    public static SosBoard toEntity(User user, SosBoardCreateInfo sosBoardCreateInfo) {
        return SosBoard.builder()
                .user(user)
                .title(sosBoardCreateInfo.title())
                .paymentType(PaymentType.fromString(sosBoardCreateInfo.paymentType()))
                .price(sosBoardCreateInfo.price())
                .comment(sosBoardCreateInfo.comment())
                .startDate(sosBoardCreateInfo.startDate())
                .endDate(sosBoardCreateInfo.endDate())
                .build();
    }

    public void addPetSosBoards(List<PetSosBoard> petSosBoards) {
        if (this.petSosBoards == null) {
            this.petSosBoards = new ArrayList<>();
        }
        this.petSosBoards.addAll(petSosBoards);
        // 양방향 연관 관계 설정
        petSosBoards.forEach(petSosBoard -> petSosBoard.addSosBoard(this));
    }

    public void updateFields(UpdateSosBoardInfo updateSosBoardInfo) {
        this.title = updateSosBoardInfo.title();
        this.paymentType = PaymentType.valueOf(updateSosBoardInfo.paymentType());
        this.price = updateSosBoardInfo.price();
        this.comment = updateSosBoardInfo.comment();
        this.startDate = updateSosBoardInfo.startDate();
        this.endDate = updateSosBoardInfo.endDate();
    }

    public void checkUserId(Long userId) {
        if (!user.getId().equals(userId)) {
            throw new SosBoardServiceException(SosBoardErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
