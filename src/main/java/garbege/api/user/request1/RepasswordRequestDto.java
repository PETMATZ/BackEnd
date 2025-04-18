package garbege.api.user.request1;

import garbege.service.user.info.RepasswordInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RepasswordRequestDto {
    private String currentPassword;
    private String newPassword;

    public static RepasswordInfo of(RepasswordRequestDto reqDto) {
        return RepasswordInfo.builder()
                .currentPassword(reqDto.getCurrentPassword())
                .newPassword(reqDto.getNewPassword())
                .build();
    }
}
