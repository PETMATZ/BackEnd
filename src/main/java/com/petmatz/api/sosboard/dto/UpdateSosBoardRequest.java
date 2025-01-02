package com.petmatz.api.sosboard.dto;

import com.petmatz.domain.sosboard.dto.UpdateSosBoardInfo;

public record UpdateSosBoardRequest(

        String title,
        String paymentType,
        int price,
        String comment,
        String startDate,
        String endDate

) {

        public UpdateSosBoardInfo of() {
                return UpdateSosBoardInfo.builder()
                        .title(title)
                        .paymentType(paymentType)
                        .price(price)
                        .comment(comment)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build();
        }

}
