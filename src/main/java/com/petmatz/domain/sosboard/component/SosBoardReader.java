package com.petmatz.domain.sosboard.component;

import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.petmatz.api.pet.dto.PetResponse;
import com.petmatz.domain.sosboard.SosBoardRepository;
import com.petmatz.domain.sosboard.dto.PageResponse;
import com.petmatz.domain.sosboard.dto.LegercySosBoardInfo;
import com.petmatz.domain.sosboard.dto.SosBoardInfo;
import com.petmatz.domain.sosboard.entity.PetSosBoard;
import com.petmatz.domain.sosboard.entity.SosBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SosBoardReader {

    private final SosBoardRepository sosBoardRepository;

    public SosBoardInfo selectAllSosBoards(String region, int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Direction.DESC, "id"));

        Slice<SosBoard> sosBoardPage;
        if (region == null || region.isEmpty()) {
            sosBoardPage = sosBoardRepository.findAll(pageable);
        } else {
            sosBoardPage = sosBoardRepository.findByUserRegion(region, pageable);
        }
        List<SosBoard> sosBoards = sosBoardPage.getContent();

        //TODO 임시로 쓰는 총 갯수임, 추후에는 hasNext로 판단할 것.
        long totalElements = sosBoardRepository.count();
        long totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        // 페이지 응답 생성
        return SosBoardInfo.of(sosBoards, totalElements,totalPages,pageNum + 1);
    }
}
