//package com.petmatz.domain.old.sosboard.dto;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.petmatz.api.pet.dto.PetResponse;
//import lombok.Builder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Builder
//public record SosBoardCreateInfo(
//
//        Long id,
//        Long userId,
//        String title,
//        String paymentType,
//        int price,
//        String comment,
//        List<Long> petIds, // 여러 펫 ID를 받음
//        List<PetResponse> petResponses, // PetResponse를 포함,
//        String startDate, // 프론트에서 받은 날짜 문자열
//        String endDate,    // 프론트에서 받은 날짜 문자열
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//        LocalDateTime createdAt, // 생성일시
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
//        LocalDateTime updatedAt
//
//) {
//}
