package com.petmatz.api.email;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.email.dto.CheckCertificationRequest;
import com.petmatz.api.user.dto.request.EmailCertificationRequest;
import com.petmatz.application.email.port.EmailUseCasePort;
import com.petmatz.infra.jwt.JwtExtractProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailUseCasePort emailUseCasePort;
    private final JwtExtractProvider jwtExtractProvider;

    @Operation(
            summary = "이메일 전송 API",
            description = "이메일 인증번호를 생성하고, 입력한 이메일로 메일을 발송합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/certification")
    public Response<Void> emailCertification(@RequestBody @Valid EmailCertificationRequest requestBody) {
        emailUseCasePort.emailCertification(requestBody.getAccountId());
        return Response.success();
    }

    @Operation(
            summary = "이메일 인증 API",
            description = "입력한 인증번호를 통해 이메일을 인증합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/check/certification")
    public Response<Void> checkCertification(@RequestBody @Valid CheckCertificationRequest requestBody){
        emailUseCasePort.checkCertification(requestBody.getAccountId(), requestBody.getCertificationNumber());
        return Response.success();
    }

    @Operation(
            summary = "비밀번호 재설정 API",
            description = "비밀번호을 재설정하여 등록된 메일로 전송합니다. ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    @PostMapping("/send/repassword")
    public Response<Void> sendRepassword() {
        String userEmail = jwtExtractProvider.findAccountIdFromJwt();
        emailUseCasePort.sendRepassword(userEmail);
        return Response.success();
    }
}
