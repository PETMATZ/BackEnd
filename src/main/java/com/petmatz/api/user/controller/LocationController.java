package com.petmatz.api.user.controller;

import com.petmatz.api.global.dto.Response;
import com.petmatz.api.user.request.UpdateLocationRequestDto;
import com.petmatz.domain.user.response.UpdateLocationResponseDto;
import com.petmatz.domain.user.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController {
    
    private final LocationService locationService;

    @PostMapping("/update-location")
    public Response<UpdateLocationResponseDto> updateLocation(@RequestBody @Valid UpdateLocationRequestDto requestBody) {
        UpdateLocationResponseDto updateLocationResponseDto = locationService.updateLocation(UpdateLocationRequestDto.of(requestBody));
        return Response.success(updateLocationResponseDto);
    }
}
