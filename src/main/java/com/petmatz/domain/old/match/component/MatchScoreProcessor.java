package com.petmatz.domain.old.match.component;

import com.petmatz.domain.old.match.dto.response.MatchScoreResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MatchScoreProcessor {

    public List<MatchScoreResponse> sortMatchScores(List<MatchScoreResponse> matchScores) {
        matchScores.sort(Comparator
                .comparingDouble(MatchScoreResponse::totalScore)
                .thenComparingDouble(MatchScoreResponse::distance)
                .reversed()
        );
        return matchScores;
    }

    public List<MatchScoreResponse> paginateMatchScores(List<MatchScoreResponse> matchScores, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, matchScores.size());
        if (start >= matchScores.size()) {
            return new ArrayList<>();
        }
        return matchScores.subList(start, end);
    }

    public int calculateTotalPages(long totalElements, int size) {
        return (int) Math.ceil((double) totalElements / size);
    }

    public List<MatchScoreResponse> filterAndSortScores(List<MatchScoreResponse> scores) {
        return scores.stream()
                .filter(score -> score.totalScore() > 0.0)
                .sorted(Comparator
                        .comparingDouble(MatchScoreResponse::totalScore)
                        .thenComparingDouble(MatchScoreResponse::distance)
                        .reversed()
                )
                .toList();
    }
}
