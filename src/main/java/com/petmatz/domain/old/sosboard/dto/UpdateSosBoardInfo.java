package com.petmatz.domain.old.sosboard.dto;

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
