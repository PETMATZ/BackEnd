package com.petmatz.domain.old.sosboard.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        long totalCount,
        int totalPages,
        int currentPage
) {
}

