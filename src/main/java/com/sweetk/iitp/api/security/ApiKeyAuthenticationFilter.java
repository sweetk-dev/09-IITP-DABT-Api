package com.sweetk.iitp.api.security;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;
import com.sweetk.iitp.api.repository.client.ClientRepository;
import com.sweetk.iitp.api.exception.ApiException;
import com.sweetk.iitp.api.exception.ErrorCode;
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
            throw new ApiException(ErrorCode.UNAUTHORIZED, "API Key가 헤더에 없습니다.");
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
                throw new ApiException(ErrorCode.UNAUTHORIZED, "허가되지 않은 API Key입니다.");
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error during API key authentication for key: {}", apiKey, e);
            throw new ApiException(ErrorCode.UNAUTHORIZED, "API Key 인증 중 오류가 발생했습니다.");
        }

        filterChain.doFilter(request, response);
    }
} 