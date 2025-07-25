package com.petmatz.domain.old.match.entity;

import com.petmatz.persistence.global.BaseEntity;
import jakarta.persistence.*;

@Entity(name = "matchResult")
@Table(name = "matchResult")
public class MatchResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId; // 매칭 요청을 보낸 유저 ID

    @Column(name = "matched_user_id", nullable = false)
    private Long matchedUserId; // 매칭된 상대 유저 ID

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MatchStatus status;
}