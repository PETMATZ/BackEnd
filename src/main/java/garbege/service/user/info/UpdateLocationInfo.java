package garbege.service.user.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateLocationInfo {
    private Double latitude;
    private Double longitude;
}
