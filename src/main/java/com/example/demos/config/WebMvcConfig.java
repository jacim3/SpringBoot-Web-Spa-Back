package com.example.demos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS 해제를 위해 백엔드에서 필요.
@Configuration      // 스프링 빈으로 등록.
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대하여
                .allowedOrigins("http://localhost:3000")        // 해당 origin이
                .allowedMethods("GET","POST", "PUT","PATCH","DELETE","OPTIONS")     // 해당 메서드 요청을허용
                .allowedHeaders("*")        // 모든 헤더
                .allowCredentials(true)     // 인증에 대한 정보
                .maxAge(MAX_AGE_SECS);      // 요청시간 설정
    }
}
