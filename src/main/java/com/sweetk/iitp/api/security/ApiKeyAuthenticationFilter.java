package com.sweetk.iitp.api.security;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.constant.DataStatusType;
import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;
import com.sweetk.iitp.api.exception.ApiException;
import com.sweetk.iitp.api.exception.ErrorCode;
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
        String path = request.getServletPath();

        log.info("Request path: {}", path);

        // permitAll() 경로는 인증 로직을 건너뜀
        if (path.equals("/v3/api-docs") ||
            path.equals("/v3/api-docs/") ||
            path.startsWith("/v3/api-docs/") ||
            path.startsWith("/docs/") ||
            path.equals("/favicon.ico") ||
//            path.startsWith(ApiConstants.ApiPath.API_V1_BASIC) ||
//             path.startsWith(ApiConstants.ApiPath.API_V1_POI) ||
            path.startsWith( ApiConstants.ApiPath.API_V1_COMMON)){
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(ApiConstants.API_KEY_HEADER);
        
        if (apiKey == null || apiKey.isEmpty()) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "API Key가 헤더에 없습니다.");
        }

        try {
            // Find active client Key by API key
            OpenApiClientKeyEntity apiClientKey = clientRepository.findActiveKeyByApiKey(apiKey)
                    .orElseThrow(() -> {
                        log.warn("API Key not found in database: {}", apiKey);
                        return new ApiException(ErrorCode.UNAUTHORIZED, "유효하지 않은 API Key 입니다.");
                    });

            // Validate client status (active and not deleted)
            OpenApiClientEntity apiClient = getAtiveOpenApiByKeyInfo(apiClientKey);
            if (apiClient == null) {
                log.warn("API Key found but client is inactive or deleted: {}", apiKey);
                throw new ApiException(ErrorCode.UNAUTHORIZED, "비활성화되거나 삭제된 클라이언트의 API Key 입니다.");
            }

            // Update latest access time for the API key
            clientRepository.updateLatestAccessTime(apiClientKey, OffsetDateTime.now());

            // Update the latest login time for the client
            clientRepository.updateLatestLoginTime(apiClient, OffsetDateTime.now());

            // Create authentication token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    apiClient,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + apiClient.getRole()))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("API Key authentication successful for client: {}", apiClient.getClientId());

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
                    && apiClientEntity.getStatus().equals(DataStatusType.ACTIVE)
                    && apiClientEntity.getDelYn().equals(SysConstants.YN_N)) {
                return apiClientEntity;
            }
        }
        return null;
    }
} 