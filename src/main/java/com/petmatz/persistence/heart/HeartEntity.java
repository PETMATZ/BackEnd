package com.petmatz.persistence.heart;

import com.petmatz.persistence.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Heart")
@Table(name = "Heart")
public class HeartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "my_id", nullable = false)
    private Long myId;

    @Column(name = "hearted_id", nullable = false)
    private Long heartedId;

    public HeartEntity(Long myId, Long heartedId) {
        this.myId = myId;
        this.heartedId = heartedId;
    }
}
