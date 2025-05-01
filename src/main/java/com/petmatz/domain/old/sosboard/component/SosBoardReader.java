package com.petmatz.domain.old.sosboard.component;

import com.petmatz.domain.old.sosboard.SosBoardRepository;
import com.petmatz.domain.old.sosboard.dto.LegercySosBoardInfo;
import com.petmatz.domain.old.sosboard.dto.PageResponse;
import com.petmatz.domain.old.sosboard.dto.SosBoardInfoList;
import com.petmatz.domain.old.sosboard.entity.PetSosBoard;
import com.petmatz.domain.old.sosboard.entity.SosBoard;
import com.petmatz.domain.old.sosboard.exception.SosBoardErrorCode;
import com.petmatz.domain.old.sosboard.exception.SosBoardServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

//    public PageResponse<LegercySosBoardInfo> getUserSosBoardsByNickname(String nickname, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
//
//        Page<SosBoard> sosBoardPage = sosBoardRepository.findByUserNickname(nickname, pageable);
//
//        // SosBoard → SosBoardServiceDto 변환
//        List<LegercySosBoardInfo> serviceDtos = sosBoardPage.getContent().stream()
//                .map(sosBoard -> {
//                    List<PetResponse> petResponses = sosBoard.getPetSosBoards().stream()
//                            .map(PetSosBoard::getPet)
//                            .map(PetResponse::of)
//                            .collect(Collectors.toList());
//                    return LegercySosBoardInfo.from(sosBoard, petResponses);
//                })
//                .collect(Collectors.toList());
//
//        // PageResponseDto 생성
//        return new PageResponse<>(
//                serviceDtos,
//                sosBoardPage.getTotalElements(),
//                sosBoardPage.getTotalPages(),
//                page + 1
//        );
//    }
}
