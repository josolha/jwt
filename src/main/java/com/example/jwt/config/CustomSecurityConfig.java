package com.example.jwt.config;

import com.example.jwt.security.APIUserDetailService;
import com.example.jwt.security.filter.APILoginFilter;
import com.example.jwt.security.filter.TokenCheckFilter;
import com.example.jwt.security.handler.APILoginSuccessHandler;
import com.example.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig {

    // 주입된 사용자 상세 서비스
    private final APIUserDetailService apiUserDetailService;

    // 주입된 JWT 유틸리티
    private final JWTUtil jwtUtill;

    // 비밀번호 암호화 방식을 BCrypt로 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 정적 리소스에 대한 보안 예외 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("-------------web configure----------");

        return (web) -> web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations());
    }

    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        // AuthenticationManager 설정을 위한 빌더 추출
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // 사용자 상세 서비스 및 비밀번호 암호화 방식 설정
        authenticationManagerBuilder
                .userDetailsService(apiUserDetailService)
                .passwordEncoder(passwordEncoder());

        // AuthenticationManager 인스턴스 생성
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        // HttpSecurity에 AuthenticationManager 설정
        http.authenticationManager(authenticationManager);

        // JWT 토큰 생성을 위한 로그인 필터 생성
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        // 로그인 성공 핸들러 설정
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtill);
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);

        // 기존의 UsernamePasswordAuthenticationFilter 이전에 apiLoginFilter 추가
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // "api"로 시작하는 모든 경로에 대한 토큰 검사 필터 추가
        http.addFilterBefore(
                tokenCheckFiler(jwtUtill),
                UsernamePasswordAuthenticationFilter.class
        );

        // CSRF 공격 방지 기능 비활성화 (일반적으로 RESTful API에서 사용하지 않음)
        http.csrf().disable();

        // 세션 정책 설정 (STATELESS로 설정하여 세션을 사용하지 않음)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    // JWT 토큰 검사를 위한 필터 생성 메서드
    private TokenCheckFilter tokenCheckFiler(JWTUtil jwtUtil) {
        return new TokenCheckFilter(jwtUtil);
    }
}

