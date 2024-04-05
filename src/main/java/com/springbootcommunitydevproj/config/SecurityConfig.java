package com.springbootcommunitydevproj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    // 특정 URL에 대해서는 Security 기능 비활성화
    // h2-console과 static 하위 정적 리소스 접근에는 Security 비활성화
    // swagger 접근도 Security 비활성화
    @Bean
    public WebSecurityCustomizer config() {
        return web -> web.ignoring()
            .requestMatchers("static/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth ->
                // 로그인, 회원가입, "/user" URL에 대해서는 모든 요청에 대해 인증 허용
                // 그 외 요청에 대해서는 인증 절차를 거친다.
                // get url 테스트 목적으로 추가함.
                auth.requestMatchers("/login", "/signup", "/user").permitAll()
                    .requestMatchers("/api/admin/**", "/admin/**").hasAuthority("Admin") // 이 API는 Admin만 접근 가능
                    .requestMatchers("/posts/그룹 모집 게시판").hasAuthority("4")  // 이 API는 4등급 이상만 접근 가능
                    .requestMatchers("/posts/평가 게시판").hasAuthority("3")  // 이 API는 3등급 이상만 접근 가능
                    .requestMatchers("/posts/정보 공유 게시판").hasAuthority("3")  // 이 API는 3등급 이상만 접근 가능
                    .anyRequest().authenticated()
            )
            .formLogin(auth ->
                // 폼 기반 로그인 페이지는 "/login" URL로 사용
                // 로그인 성공 시 "/posts/자유 게시판" URL로 이동
                auth.loginPage("/login")
                    .defaultSuccessUrl("/posts/자유 게시판"))
            .logout(auth ->
                // 로그 아웃 성공 시 "/login" URL로 이동
                // 로그 아웃 이후 사용자 Session을 전체 삭제한다.
                auth.logoutSuccessUrl("/login")
                    .invalidateHttpSession(true))
            .csrf(auth ->
                // csrf 비활성화
                auth.disable())
            .build();
    }

    // 비밀번호 인코더로 사용할 Bean 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
