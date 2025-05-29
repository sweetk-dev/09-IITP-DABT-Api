package com.sweetk.iitp.api.config;

import com.sweetk.iitp.api.interceptor.ApiKeyAuthInterceptor;
import com.sweetk.iitp.api.interceptor.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;
    private final ApiKeyAuthInterceptor apiKeyAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // API Key 인증 인터셉터 등록
        registry.addInterceptor(apiKeyAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/v1/auth/**"); // 인증 관련 엔드포인트는 제외

        // Rate Limiting 인터셉터 등록
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/api/**");
    }
} 