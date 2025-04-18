package com.petmatz.domain.old.sosboard.component;

import com.petmatz.domain.old.sosboard.SosBoardRepository;
import com.petmatz.domain.old.sosboard.entity.SosBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SosBoardAppend {

    private final SosBoardRepository sosBoardRepository;

    public SosBoard append(SosBoard sosBoard) {
        return sosBoardRepository.save(sosBoard);
    }

}
