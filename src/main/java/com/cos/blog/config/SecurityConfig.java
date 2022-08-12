package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 빈 등록(IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록됨
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig {

    @Bean // IoC가 됨
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    };

    // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게하는 것.
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/auth/loginForm");


        return http.build();
    }
}
