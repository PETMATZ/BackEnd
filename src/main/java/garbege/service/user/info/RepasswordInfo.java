package garbege.service.user.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepasswordInfo {
    private String currentPassword;
    private String newPassword;
}
