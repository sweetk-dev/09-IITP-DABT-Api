package com.sweetk.iitp.api.config;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.repository.ApiClientRepository;
import com.sweetk.iitp.api.security.ApiKeyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApiClientRepository apiClientRepository;

    public SecurityConfig(ApiClientRepository apiClientRepository) {
        this.apiClientRepository = apiClientRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                // OpenAPI 문서 접근 허용
                .requestMatchers("/v3/api-docs/**").permitAll()
                // API 엔드포인트 설정
                .requestMatchers(ApiConstants.ApiPath.API_V1_BASIC + "/**").permitAll()
                .requestMatchers(ApiConstants.ApiPath.API_V1_POI + "/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(new ApiKeyAuthenticationFilter(apiClientRepository), 
                           UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(401);
                    response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Invalid API Key\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(403);
                    response.getWriter().write("{\"error\":\"Forbidden\",\"message\":\"API Key not authorized for this resource\"}");
                })
            .and()
            .build();
    }
} 