package garbege.service.user.info;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckCertificationInfo {
    private String accountId;

    private String certificationNumber;
}
