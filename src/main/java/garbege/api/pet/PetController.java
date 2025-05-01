//package com.petmatz.api.pet;
//
//import com.petmatz.api.global.dto.Response;
//import com.petmatz.api.global.dto.S3ImgDataResponse;
//import com.petmatz.api.pet.dto.PetInfoResponse;
//import com.petmatz.api.pet.dto.PetRegisterNoRequest;
//import com.petmatz.api.pet.dto.PetRequest;
//import com.petmatz.api.pet.dto.PetUpdateRequest;
//import com.petmatz.infra.jwt.JwtExtractProvider;
//import com.petmatz.domain.global.S3ImgDataInfo;
//import com.petmatz.domain.pet.PetService;
//import com.petmatz.domain.pet.dto.OpenApiPetInfo;
//import com.petmatz.domain.user.component.UserUtils;
//import com.petmatz.persistence.user.User;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameters;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.MalformedURLException;
//
//
//@RestController
//@RequestMapping("/api/pets")
//@RequiredArgsConstructor
//public class PetController {
//
//    private final PetService petService;
//    private final UserUtils userUtils;
//    private final JwtExtractProvider jwtExtractProvider;
//
//    // 동물등록번호 조회
//    //TODO 이 부분 삭제
//    @PostMapping("/fetch")
//    @Operation(summary = "동물등록번호 조회", description = "외부 API를 통해 동물등록번호 정보를 조회합니다.")
//    @Parameters({
//            @Parameter(name = "dogRegNo", description = "강아지 고유 No", example = "1123412"),
//            @Parameter(name = "ownerNm", description = "사용자 이름", example = "홍길동"),
//    })
//    public Response<PetInfoResponse> fetchPetInfo(@RequestBody PetRegisterNoRequest request) {
//        OpenApiPetInfo openApiPetInfo = petService.fetchPetInfo(request.dogRegNo(), request.ownerNm());
//        PetInfoResponse responseDto = PetInfoResponse.of(openApiPetInfo);
//        return Response.success(responseDto);
//    }
//
//    // 댕댕이 정보 등록
//    @PostMapping("/register")
//    @Operation(summary = "반려동물 등록", description = "사용자의 반려동물 정보를 등록합니다.")
//    @Parameters({
//            @Parameter(name = "id", description = "뭔지 모르겠음", example = "?"),
//            @Parameter(name = "dogRegNo", description = "강아지 등록 넘버", example = "12341512"),
//            @Parameter(name = "ownerNm", description = "반려견 주인 이름", example = "홍길동"),
//            @Parameter(name = "petName", description = "반려견 이름", example = "예삐"),
//            @Parameter(name = "breed", description = "반려견 견종", example = "셰퍼드"),
//            @Parameter(name = "gender", description = "반려견 성별", example = ""),
//            @Parameter(name = "neuterYn", description = "중성화 여부", example = ""),
//            @Parameter(name = "size", description = "반려견 사이즈", example = ""),
//            @Parameter(name = "age", description = "반려견 나이", example = "12"),
//            @Parameter(name = "temperament", description = "반려견 성격", example = "차분한"),
//            @Parameter(name = "preferredWalkingLocation", description = "선호 장소", example = "공원"),
//            @Parameter(name = "profileImg", description = "반려견 사진", example = "홍길동 or 빈값"),
//            @Parameter(name = "comment", description = "간단 설명", example = "산책을 좋아합니다."),
//    })
//    public Response<S3ImgDataResponse> registerPet(@RequestBody PetRequest request) throws MalformedURLException {
//        Long userId = jwtExtractProvider.findIdFromJwt();
//        User user = userUtils.findIdUser(userId);
//        S3ImgDataInfo petSaveInfo = petService.savePet(user, request.of());
//        return Response.success(S3ImgDataResponse.of(petSaveInfo));
//    }
//
//    // 댕댕이 정보 수정
//    @PutMapping("/{id}")
//    @Operation(summary = "반려동물 정보 수정", description = "기존 반려동물 정보를 수정합니다.")
//    @Parameters({
//            @Parameter(name = "id", description = "반려동물 고유 NO", example = "1"),
//            @Parameter(name = "breed", description = "반려견 견종", example = "셰퍼드"),
//            @Parameter(name = "gender", description = "반려견 성별", example = ""),
//            @Parameter(name = "petName", description = "반려견 이름", example = "예삐"),
//            @Parameter(name = "neuterYn", description = "중성화 여부", example = ""),
//            @Parameter(name = "size", description = "반려견 사이즈", example = ""),
//            @Parameter(name = "age", description = "반려견 나이", example = "12"),
//            @Parameter(name = "temperament", description = "반려견 성격", example = "차분한"),
//            @Parameter(name = "profileImg", description = "반려견 사진", example = "홍길동 or 빈값"),
//            @Parameter(name = "comment", description = "간단 설명", example = "산책을 좋아합니다."),
//    })
//    public Response<S3ImgDataResponse> updatePet(@PathVariable Long id, @RequestBody PetUpdateRequest petUpdateRequest) throws MalformedURLException {
//        Long userId = jwtExtractProvider.findIdFromJwt();
//        User user = userUtils.findIdUser(userId);
//        S3ImgDataInfo petSaveInfo = petService.updatePet(id, user, petUpdateRequest.of());
//        return Response.success(S3ImgDataResponse.of(petSaveInfo));
//    }
//
//    // 댕댕이 정보 삭제
//    @DeleteMapping("/{id}")
//    @Operation(summary = "반려동물 삭제", description = "등록된 반려동물을 삭제합니다.")
//    @Parameter(name = "id", description = "반려동물 ID", example = "1")
//    public Response<Void> deletePet(@PathVariable Long id) {
//        Long userId = jwtExtractProvider.findIdFromJwt();
//        User user = userUtils.findIdUser(userId);
//        petService.deletePet(id, user);
//        return Response.success("댕댕이 정보가 성공적으로 삭제되었습니다.");
//    }
//}