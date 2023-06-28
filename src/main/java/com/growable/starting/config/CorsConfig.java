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
        config.addAllowedOrigin("*"); // 로컬 환경용 '*'을 포함한 추가적인 리모트 서버 환경을 추가
        config.addAllowedOrigin("http://3.38.244.197:3000");
        config.addAllowedHeader("*"); // 모든 헤더 응답 허용
        config.addExposedHeader("*");
        config.addAllowedMethod("*"); // 모든 요청 메서드 응답 허용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
