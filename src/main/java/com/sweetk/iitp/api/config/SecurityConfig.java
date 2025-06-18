package com.sweetk.iitp.api.config;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.repository.client.ClientRepository;
import com.sweetk.iitp.api.security.ApiKeyAuthenticationFilter;
import com.sweetk.iitp.api.security.RateLimitFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ClientRepository clientRepository;
    private final RateLimitFilter rateLimitFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers(ApiConstants.ApiPath.API_V1_BASIC + "/**").permitAll()
                .requestMatchers(ApiConstants.ApiPath.API_V1_POI + "/**").permitAll()
                .requestMatchers("/health", "/version").permitAll()
                .anyRequest().denyAll()
            )
            .addFilterBefore(new ApiKeyAuthenticationFilter(clientRepository), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
} 