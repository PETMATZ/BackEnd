package com.petmatz.api.sosboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petmatz.api.pet.dto.PetResponse;
import com.petmatz.domain.old.sosboard.dto.SosBoardCreateInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public record SosBoardCreateRequest(
        Long id,
        Long userId,
        String title,
        String paymentType,
        int price,
        String comment,
        List<Long> petIds, // 여러 펫 ID를 받음
        List<PetResponse> petResponses, // PetResponse를 포함,
        String startDate, // 프론트에서 받은 날짜 문자열
        String endDate,    // 프론트에서 받은 날짜 문자열
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        LocalDateTime createdAt, // 생성일시
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        LocalDateTime updatedAt

) {
    // 변환 메서드, 컨트롤러 계층에서 요청 데이터를 서비스 계층으로 전달할 때 변환함
    public SosBoardCreateInfo of() {
        return SosBoardCreateInfo.builder()
                .id(id)
                .title(title)
                .paymentType(paymentType)
                .price(price)
                .comment(comment)
                .petIds(petIds)
                .petResponses(petResponses)
                .startDate(startDate)
                .endDate(endDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

    }


    // 날짜 형식 유효성 검증
    private void validateDateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime.parse(date, formatter); // 유효성 검증
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + date);
        }
    }
}
