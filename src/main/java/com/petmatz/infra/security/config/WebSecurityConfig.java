package com.petmatz.infra.security.config;

import com.petmatz.infra.auth.oauth.CustomOAuth2UserService;
import com.petmatz.infra.security.filter.JwtAuthenticationFilter;
import com.petmatz.infra.security.handler.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final FailedAuthenticationEntryPoint failedAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
                .csrf(CsrfConfigurer::disable) // CSRF 비활성화
                .httpBasic(HttpBasicConfigurer::disable) // HTTP Basic 인증 비활성화
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화 (JWT 사용)
                .authorizeHttpRequests(authRequests -> authRequests
                        .requestMatchers("/", "/api/auth/**", "/oauth2/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/sosboard").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/sosboard/user/{nickname}").permitAll()
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        //스웨거 모든 접근 허용
                        .requestMatchers(
                                "/swagger-ui/**",   
                                "/v3/api-docs/**",  
                                "/swagger-resources/**",
                                "/webjars/**"       
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/authorization")) // OAuth2 로그인 엔드포인트
                        .redirectionEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/callback/*")) // 리디렉션 엔드포인트
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2UserService)) // 사용자 정보 서비스
                        .successHandler(oAuthSuccessHandler) // OAuth2 성공 핸들러
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(failedAuthenticationEntryPoint)) // 인증 실패 핸들러
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*"); // 모든 Origin 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}

