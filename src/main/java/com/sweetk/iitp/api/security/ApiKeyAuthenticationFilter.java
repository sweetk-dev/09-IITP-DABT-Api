package com.sweetk.iitp.api.security;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.entity.ApiClientEntity;
import com.sweetk.iitp.api.repository.ApiClientRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final ApiClientRepository apiClientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String apiKey = request.getHeader(ApiConstants.API_KEY_HEADER);
        
        if (apiKey == null || apiKey.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        ApiClientEntity apiClient = apiClientRepository.findByApiKey(apiKey)
                .orElse(null);

        if (apiClient != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                apiClient,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + apiClient.getRole()))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
} 