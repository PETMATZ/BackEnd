package com.petmatz.persistence.heart.adapter;

import com.petmatz.domain.heart.Heart;
import com.petmatz.domain.heart.port.HeartCommendPort;
import com.petmatz.domain.heart.port.HeartQueryPort;
import com.petmatz.persistence.heart.HeartEntity;
import com.petmatz.persistence.heart.repository.HeartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class HeartAdapter implements HeartCommendPort, HeartQueryPort {

    private final HeartRepository heartRepository;

    @Override
    @Transactional
    public void toggleHeart(Long myId, Long heartId) {
        Optional<HeartEntity> existingHeart = heartRepository.findByMyIdAndHeartedId(myId, heartId);
        if (existingHeart.isPresent()) {
            heartRepository.delete(existingHeart.get());
            return; // 혜제
        }
        heartRepository.save(new HeartEntity(myId, heartId));
    }

    @Override
    @Transactional
    public List<Heart> getHeartedUsers(Long myId) {
        List<HeartEntity> heartList = heartRepository.findAllByMyId(myId);
        return heartList.stream().map(list ->
                new Heart(list.getId(), list.getMyId(), list.getHeartedId())
        ).toList();
    }

    @Override
    public boolean existsByMyIdAndHeartedId(Long myId, Long userId) {
        return !heartRepository.existsByMyIdAndHeartedId(myId, userId);
    }
}
