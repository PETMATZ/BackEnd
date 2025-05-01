//package com.petmatz.domain.old.sosboard.component;
//
//import com.petmatz.domain.old.sosboard.SosBoardRepository;
//import com.petmatz.domain.old.sosboard.entity.SosBoard;
//import com.petmatz.domain.user.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class SosBoardDelete {
//
//    private final SosBoardRepository sosBoardRepository;
//
//    //게시글 삭제
//    public void deleteSosBoard(SosBoard sosBoard, User user) {
//        //사용자 권한 확인
//        sosBoard.checkUserId(user.getId());
//        sosBoardRepository.delete(sosBoard);
//    }
//
//    public void deleteSosBoardByUser(Long userId) {
//        sosBoardRepository.deleteByUserId(userId);
//    }
//
//}
