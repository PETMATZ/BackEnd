package com.petmatz.persistence.caht.entity;

import com.petmatz.persistence.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long caregiverId;

    private Long entrustedId;

    public ChatRoomEntity(Long caregiverId, Long entrustedId) {
        this.caregiverId = caregiverId;
        this.entrustedId = entrustedId;
    }
}
