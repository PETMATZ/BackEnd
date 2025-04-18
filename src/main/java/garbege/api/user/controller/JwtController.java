//package garbege.api.user.controller;
//
//import com.petmatz.api.global.dto.Response;
//import com.petmatz.infra.jwt.JwtManager;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class JwtController {
//
//    private final JwtManager jwtManager;
//
//    @PostMapping("/token/reissue")
//    public Response<Void> reissueAccessToken(String refreshToken) {
//        jwtManager.refreshAccessToken(refreshToken);
//        return Response.success();
//    }
//}
