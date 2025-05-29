package com.sweetk.iitp.api.security;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.entity.ApiClient;
import com.sweetk.iitp.api.exception.ApiException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.ApiClientRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final ApiClientRepository apiClientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String apiKey = request.getHeader(ApiConstants.Auth.AUTH_HEADER);
        
        if (apiKey != null) {
            ApiClient client = apiClientRepository.findByApiKey(apiKey)
                    .orElseThrow(() -> new ApiException(ErrorCode.UNAUTHORIZED));

            if (!client.isActive()) {
                throw new ApiException(ErrorCode.UNAUTHORIZED, "API client is inactive");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    client.getClientId(),
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority(client.getRole()))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api-docs") || 
               path.startsWith(ApiConstants.ApiPath.API_V1_BASIC);
    }
} 