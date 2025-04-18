package garbege.api.user.controller;

import com.petmatz.api.global.dto.Response;
import garbege.api.user.request1.RepasswordRequestDto;
import garbege.api.user.request1.SendRepasswordRequestDto;
import garbege.service.user.service.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/send-repassword")
    public Response<Void> sendRepassword(@RequestBody @Valid SendRepasswordRequestDto requestBody) {
        passwordService.sendRepassword(requestBody);
        return Response.success();
    }

    @PostMapping("/repassword")
    public Response<Void> repassword(@RequestBody @Valid RepasswordRequestDto requestBody) {
        passwordService.repassword(RepasswordRequestDto.of(requestBody));
        return Response.success();
    }
}
