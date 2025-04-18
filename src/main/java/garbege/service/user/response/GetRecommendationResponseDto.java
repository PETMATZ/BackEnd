package garbege.service.user.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetRecommendationResponseDto{

    private boolean isRecommended;
    public GetRecommendationResponseDto(boolean isRecommended) {
        this.isRecommended=isRecommended;
    }

    public static ResponseEntity<GetRecommendationResponseDto> success(boolean isRecommended) {
        GetRecommendationResponseDto responseBody = new GetRecommendationResponseDto(isRecommended);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
