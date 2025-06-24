package com.sweetk.iitp.api.security;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.constant.DataStatusType;
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
import java.util.Optional;

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
            // Find active client Key by API key
            OpenApiClientKeyEntity apiClientKey = clientRepository.findActiveKeyByApiKey(apiKey)
                                                    .orElse(null);

            if (apiClientKey != null) {
                OpenApiClientEntity apiClient = getAtiveOpenApiByKeyInfo(apiClientKey);
                if (apiClient != null) {
                    // Update latest access time for the API key
                    clientRepository.updateLatestAccessTime(apiClientKey.getId(), OffsetDateTime.now());

                    // Update latest login time for the client
                    clientRepository.updateLatestLoginTime(apiClient.getApiCliId(), OffsetDateTime.now());
                }

            }

            OpenApiClientEntity apiClient = clientRepository.findActiveClientByApiKey(apiKey)
                    .orElse(null);

            if (apiClient != null) {
                // Update latest access time for the API key
                OpenApiClientKeyEntity keyEntity = clientRepository.findAtiveByApiKey(apiKey)
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
                throw new ApiException(ErrorCode.UNAUTHORIZED, "유효하지않은 인증 KEY 입니다.");
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error during API key authentication for key: {}", apiKey, e);
            throw new ApiException(ErrorCode.UNAUTHORIZED, "API Key 인증 처리중 오류가 발생했습니다.");
        }

        filterChain.doFilter(request, response);
    }


    private OpenApiClientEntity getAtiveOpenApiByKeyInfo(OpenApiClientKeyEntity keyEntity) {
        if (keyEntity != null) {
            OpenApiClientEntity apiClientEntity = keyEntity.getOpenApiClient();
            if(apiClientEntity != null
                    && !apiClientEntity.getIsDeleted()
                    && apiClientEntity.getStatus().equals(DataStatusType.ACTIVE)) {
                return apiClientEntity;
            }
        }
        return null;
    }
} 