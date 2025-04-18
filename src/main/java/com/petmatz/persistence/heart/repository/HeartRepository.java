package com.petmatz.persistence.heart.repository;

import com.petmatz.persistence.heart.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<HeartEntity, Long> {
    boolean existsByMyIdAndHeartedId(Long myId, Long heartedId);
    List<HeartEntity> findAllByMyId(Long myId);
    Optional<HeartEntity> findByMyIdAndHeartedId(Long id, Long heartedId);
}
