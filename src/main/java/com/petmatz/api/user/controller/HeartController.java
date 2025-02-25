package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.HeartedUserDto;
import com.petmatz.api.user.request.HeartingRequestDto;
import com.petmatz.domain.user.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/hearting")
    public Response<Void> hearting(@RequestBody @Valid HeartingRequestDto requestBody) {
        heartService.hearting(requestBody);
        return Response.success();
    }

    @GetMapping("/get-heartlist") // 이거 테스트 필요
    public Response<List<HeartedUserDto>> getHeartedList() {
        List<HeartedUserDto> heartedList = heartService.getHeartedList();
        return Response.success(heartedList);
    }
}
