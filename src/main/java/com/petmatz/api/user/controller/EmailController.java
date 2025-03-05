package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.EmailCertificationRequestDto;
import com.petmatz.domain.user.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/email-certification")
    public Response<Void> emailCertification(@RequestBody @Valid EmailCertificationRequestDto requestBody) {
        emailService.emailCertification(requestBody);
        return Response.success();
    }

}
