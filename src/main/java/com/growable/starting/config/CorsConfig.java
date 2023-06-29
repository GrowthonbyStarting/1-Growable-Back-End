package com.growable.starting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    // CORS 필터를 스프링 IoC 컨테이너로 등록
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버 응답 시 JSON을 자바스크립트에서 처리할 수 있음
        config.addAllowedOriginPattern("*"); // 모든 도메인 패턴 허용
        config.addAllowedHeader("*"); // 모든 헤더 응답 허용
        config.addExposedHeader("*");
        config.addAllowedMethod("*"); // 모든 요청 메서드 응답 허용

        // 각 엔드포인트에 대해 별도의 CORS 설정을 적용하지 않고 단일 CORS 설정을 사용하도록 변경
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
