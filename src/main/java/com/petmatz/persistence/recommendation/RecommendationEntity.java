package com.petmatz.persistence.recommendation;

import com.petmatz.persistence.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Recommendation")
@Table(name = "Recommendation")
public class RecommendationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "my_id", nullable = false)
    private Long myId;

    @Column(name = "recommended_id", nullable = false)
    private Long recommendedId;
}
