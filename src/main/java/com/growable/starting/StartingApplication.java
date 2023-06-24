package com.growable.starting;

import com.growable.starting.jwt.JwtRequestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StartingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartingApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtRequestFilterBean(JwtRequestFilter jwtRequestFilter) {
        FilterRegistrationBean<JwtRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>(jwtRequestFilter);
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }

}
