package com.petmatz.domain.sosboard;

import com.petmatz.api.pet.dto.PetResponse;
import com.petmatz.domain.pet.component.PetReader;
import com.petmatz.domain.pet.entity.Pet;
import com.petmatz.domain.pet.repository.PetRepository;
import com.petmatz.domain.sosboard.component.SosBoardAppend;
import com.petmatz.domain.sosboard.component.SosBoardDelete;
import com.petmatz.domain.sosboard.component.SosBoardReader;
import com.petmatz.domain.sosboard.dto.*;
import com.petmatz.domain.sosboard.entity.PetSosBoard;
import com.petmatz.domain.sosboard.entity.SosBoard;
import com.petmatz.domain.sosboard.exception.SosBoardErrorCode;
import com.petmatz.domain.sosboard.exception.SosBoardServiceException;
import com.petmatz.domain.user.entity.User;
import com.petmatz.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SosBoardService {

    private final SosBoardReader sosBoardReader;
    private final SosBoardAppend sosBoardAppend;
    private final PetReader petReader;

    private final SosBoardDelete sosBoardDelete;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    // 전체 조회 (지역 필터링 + 인덱스 기반 페이지네이션)
    public SosBoardInfoList getAllSosBoards(String region, int pageNum, int size) {
        // 페이지 응답 생성
        return sosBoardReader.selectAllSosBoards(region, pageNum, size);
    }


    // 게시글 작성
    public SosBoardInfo createSosBoard(SosBoardCreateInfo sosBoardCreateInfo, User user) {
        SosBoard sosBoard = SosBoard.toEntity(user, sosBoardCreateInfo);

        List<Pet> petList = petReader.getPetList(sosBoardCreateInfo.petIds());

        List<PetSosBoard> petSosBoards = petList.stream()
                .map(pet -> {
                    return PetSosBoard.builder()
                            .sosBoard(sosBoard)
                            .pet(pet)
                            .build();
                })
                .collect(Collectors.toList());

        sosBoard.addPetSosBoards(petSosBoards);

        SosBoard savedBoard = sosBoardAppend.append(sosBoard);

        return SosBoardInfo.of(savedBoard, petList);
    }


    // 특정 게시물 조회
    public SpecificSosBoardInfo getSosBoardById(Long boardId) {

        SosBoard sosBoard = sosBoardReader.selectSosBoard(boardId);

        // SosBoardServiceDto 반환
        return SpecificSosBoardInfo.of(sosBoard);
    }

    // 펫 정보 업데이트
    public SpecificSosBoardInfo updateSosBoard(Long boardId, UpdateSosBoardInfo updateSosBoardInfo, User user) {
        // 기존 게시글 조회
        SosBoard sosBoard = sosBoardReader.selectSosBoard(boardId);

        //사용자 권한 확인
        sosBoard.checkUserId(user.getId());

        // 게시글의 기타 필드 업데이트
        sosBoard.updateFields(updateSosBoardInfo);


        return SpecificSosBoardInfo.of(sosBoard);
    }

    //게시글 삭제
    public void deleteSosBoard(Long boardId, User user) {
        SosBoard sosBoard = sosBoardReader.selectSosBoard(boardId);
        sosBoardDelete.deleteSosBoard(sosBoard, user);
    }

    // User의 Pet 정보 불러오기
    public List<PetResponse> getUserPets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SosBoardServiceException(SosBoardErrorCode.USER_NOT_FOUND));

        // Pet 엔티티를 DTO로 변환
        return petRepository.findByUserId(user.getId()).stream()
                .map(SosBoardPet::of) // Pet → SosBoardPetDto 변환
                .map(PetResponse::of)    // SosBoardPetDto → PetResponse 변환
                .collect(Collectors.toList());
    }

    // 해당 닉네임에 해당하는 글 불러오기
    public PageResponse<LegercySosBoardInfo> getUserSosBoardsByNickname(String nickname, int page, int size) {
        return sosBoardReader.getUserSosBoardsByNickname(nickname, page, size);
    }
}



