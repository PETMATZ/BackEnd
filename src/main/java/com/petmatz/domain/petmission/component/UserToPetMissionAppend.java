package com.petmatz.domain.petmission.component;

import com.petmatz.domain.petmission.entity.UserToPetMissionEntity;
import com.petmatz.domain.petmission.repository.UserToPetMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserToPetMissionAppend {

    private final UserToPetMissionRepository userToPetMissionRepository;

    public void insertUserToPetMission(List<UserToPetMissionEntity> list) {
        userToPetMissionRepository.saveAll(list);
    }

}
