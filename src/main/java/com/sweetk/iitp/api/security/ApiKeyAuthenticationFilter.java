package com.sweetk.iitp.api.security;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;
import com.sweetk.iitp.api.repository.client.ClientRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final ClientRepository clientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String apiKey = request.getHeader(ApiConstants.API_KEY_HEADER);
        
        if (apiKey == null || apiKey.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Find active client by API key
            OpenApiClientEntity apiClient = clientRepository.findActiveClientByApiKey(apiKey)
                    .orElse(null);

            if (apiClient != null && !apiClient.getIsDeleted()) {
                // Update latest access time for the API key
                OpenApiClientKeyEntity keyEntity = clientRepository.findByApiKeyAndIsActiveTrueAndIsDeletedFalse(apiKey)
                        .orElse(null);
                if (keyEntity != null) {
                    clientRepository.updateLatestAccessTime(keyEntity.getId(), OffsetDateTime.now());
                }
                
                // Update latest login time for the client
                clientRepository.updateLatestLoginTime(apiClient.getApiCliId(), OffsetDateTime.now());
                
                // Create authentication token
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    apiClient,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + apiClient.getRole()))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                log.debug("API Key authentication successful for client: {}", apiClient.getClientId());
            } else {
                log.warn("Invalid or inactive API key provided: {}", apiKey);
            }
        } catch (Exception e) {
            log.error("Error during API key authentication for key: {}", apiKey, e);
        }

        filterChain.doFilter(request, response);
    }
} 