package com.petmatz.domain.old.petmission.component;

import com.petmatz.domain.old.petmission.entity.UserToPetMissionEntity;
import com.petmatz.domain.old.petmission.repository.UserToPetMissionRepository;
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
