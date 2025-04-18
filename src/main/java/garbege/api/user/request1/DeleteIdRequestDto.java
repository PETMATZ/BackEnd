package garbege.api.user.request1;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteIdRequestDto {
    @NotBlank
    private String password;
}