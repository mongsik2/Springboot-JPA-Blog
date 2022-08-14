package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 빈 등록(IoC관리)
@EnableWebSecurity // 시큐리티 필터가 등록됨
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig {

    @Autowired
    private PrincipalDetailService principalDetailService;


    @Bean // IoC가 됨
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    };


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게하는 것.
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 시큐리티가 대신 로그인해주기 때문에 password를 가로채기를 하는데
        // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지를 알아야
        // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());


        http
                .csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)


                .authorizeRequests(authorize -> authorize
                        .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 오는 요청을 가로채서 대신 로그인
                .defaultSuccessUrl("/"); // 로그인 성공시 이동 경로 "/"


        return http.build();
    }
}
