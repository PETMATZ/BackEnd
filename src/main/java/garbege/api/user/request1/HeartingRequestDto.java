package garbege.api.user.request1;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeartingRequestDto {
    @NotBlank
    private Long heartedId;
}
