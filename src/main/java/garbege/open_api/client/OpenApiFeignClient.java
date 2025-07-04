//package garbege.open_api.client;
//
//import garbege.open_api.config.OpenApiFeignClientConfiguration;
//import garbege.open_api.dto.OpenApiResponse;
//import garbege.open_api.vo.PetInfo;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
//@FeignClient(
//        name = "openApi",
//        url = "http://apis.data.go.kr/1543061/animalInfoSrvc",
//        configuration = OpenApiFeignClientConfiguration.class)
//public interface OpenApiFeignClient {
//
//    @GetMapping("/animalInfo")
//    OpenApiResponse<PetInfo> getAnimalInfo(
//            @RequestParam(value = "dog_reg_no") String dogRegNo,
//            @RequestParam(value = "owner_nm") String ownerNm
//            );
//
//}
