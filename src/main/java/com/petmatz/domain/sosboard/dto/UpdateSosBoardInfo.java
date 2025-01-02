package com.petmatz.domain.sosboard.dto;

import lombok.Builder;

@Builder
public record UpdateSosBoardInfo(

        String title,
        String paymentType,
        int price,
        String comment,
        String startDate,
        String endDate

) {



}
