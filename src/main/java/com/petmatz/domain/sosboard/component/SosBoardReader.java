package com.petmatz.domain.sosboard.component;

import com.petmatz.domain.sosboard.SosBoardRepository;
import com.petmatz.domain.sosboard.dto.SosBoardInfoList;
import com.petmatz.domain.sosboard.entity.SosBoard;
import com.petmatz.domain.sosboard.exception.SosBoardErrorCode;
import com.petmatz.domain.sosboard.exception.SosBoardServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SosBoardReader {

    private final SosBoardRepository sosBoardRepository;

    public SosBoardInfoList selectAllSosBoards(String region, int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Direction.DESC, "id"));

        Slice<SosBoard> sosBoardPage = checkRegin(region, pageable);

        List<SosBoard> sosBoards = sosBoardPage.getContent();

        //TODO 임시로 쓰는 총 갯수임, 추후에는 hasNext로 판단할 것.
        long totalElements = sosBoardRepository.count();
        long totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        // 페이지 응답 생성
        return SosBoardInfoList.of(sosBoards, totalElements,totalPages,pageNum + 1);
    }

    public SosBoard selectSosBoard(Long boardId) {
        return sosBoardRepository.findById(boardId)
                .orElseThrow(() -> new SosBoardServiceException(SosBoardErrorCode.BOARD_NOT_FOUND));
    }

    private Slice<SosBoard> checkRegin(String region, Pageable pageable) {
        if (region == null || region.isEmpty()) {
            return sosBoardRepository.findAll(pageable);
        } else {
            return sosBoardRepository.findByUserRegion(region, pageable);
        }
    }
}
