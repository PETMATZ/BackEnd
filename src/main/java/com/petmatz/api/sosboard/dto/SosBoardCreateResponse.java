//package com.petmatz.api.sosboard.dto;
//
//import com.petmatz.api.pet.dto.PetResponse;
//import com.petmatz.domain.old.sosboard.PaymentType;
//import com.petmatz.domain.old.sosboard.entity.PetSosBoard;
//import com.petmatz.domain.old.sosboard.entity.SosBoard;
//import com.petmatz.garbege.service.user.User;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public record SosBoardCreateResponse(
//        Long id,
//        String title,
//        String comment,
//        PaymentType paymentType,
//        int price,
//        String startDate,
//        String endDate,
//        List<PetResponse> pets,
//        String authorNickname,
//        String authorProfileImg,
//        String authorGender,
//        String authorRegion
//) {
//    public static SosBoardCreateResponse of(SosBoard sosBoard) {
//        User user = sosBoard.getUser();
//        return new SosBoardCreateResponse(
//                sosBoard.getId(),
//                sosBoard.getTitle(),
//                sosBoard.getComment(),
//                sosBoard.getPaymentType(),
//                sosBoard.getPrice(),
//                sosBoard.getStartDate(),
//                sosBoard.getEndDate(),
//                sosBoard.getPetSosBoards().stream()
//                        .map(PetSosBoard::getPet)
//                        .map(PetResponse::of)
//                        .collect(Collectors.toList()),
//                user.getNickname(),
//                user.getProfileImg(),
//                user.getGender().toString(),
//                user.getRegion()
//        );
//    }
//}
