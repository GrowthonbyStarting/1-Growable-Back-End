package com.growable.starting.config;

import com.growable.starting.jwt.JwtAuthenticationEntryPoint;
import com.growable.starting.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


// Oauth 로그인 진행 순서
// 1. 인가 코드 발급(회원 인증)
// 2. 엑세스 토큰 발급(접근 권한 부여)
// 3. 액세스 토큰을 이용해 사용자 정보 불러오기
// 4. 불러온 사용자 정보를 토대로 자동 회원가입/로그인 진행
// ※ 소셜 플랫폼의 로그인과 프로젝트 앱의 로그인은 별개임!!

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String FRONT_URL = "http://ec2-13-209-18-185.ap-northeast-2.compute.amazonaws.com:3000";
    private final CorsFilter corsFilter;

    public SecurityConfig(CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }

    // @Bean -> 해당 메소드의 리턴되는 오브젝트를 IoC로 등록해줌
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement()  // session 을 사용하지 않음
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .addFilter(corsFilter) // @CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
                .oauth2Login().defaultSuccessUrl("/main");
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/v2/api-docs/**", "/webjars/**", FRONT_URL + "/auth/**",FRONT_URL + "/oauth/**", FRONT_URL + "/main/**"
                ,"/api/**")
                .permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        http
                .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}