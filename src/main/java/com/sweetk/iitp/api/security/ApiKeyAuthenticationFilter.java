package com.sweetk.iitp.api.security;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.entity.openapi.OpenApiAuthKeyEntity;
import com.sweetk.iitp.api.entity.openapi.OpenApiUserEntity;
import com.sweetk.iitp.api.exception.ApiException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.openapi.openApiRepository;
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

    private final openApiRepository openApiRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();
        String queryString = request.getQueryString();
        String fullPath = queryString != null ? path + "?" + queryString : path;

        log.info("Request path: {}, Query: {}", path, queryString);
        log.debug("Full request URL: {}", fullPath);

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
            // Find and validate API key (active_yn='Y', del_yn='N' by repository)
            OpenApiAuthKeyEntity apiAuthKey = openApiRepository.findActiveKeyByApiKey(apiKey)
                    .orElseThrow(() -> {
                        log.warn("API Key not found or inactive: {}", apiKey);
                        return new ApiException(ErrorCode.UNAUTHORIZED, "유효하지 않은 API Key 입니다.");
                    });

            // Validate key validity period (start_dt ~ end_dt)
            validateKeyPeriod(apiAuthKey);

            // Update latest access time
            openApiRepository.updateLatestAccessTime(apiAuthKey, OffsetDateTime.now());

            // Load user for authentication context
            OpenApiUserEntity apiUser = openApiRepository.findClientByUserId(Long.valueOf(apiAuthKey.getUserId()))
                    .orElseThrow(() -> new ApiException(ErrorCode.UNAUTHORIZED, "사용자 정보를 찾을 수 없습니다."));
            
            // Update latest login time
            openApiRepository.updateLatestLoginTime(apiUser, OffsetDateTime.now());

            // Create authentication token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    apiUser,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + apiUser.getRole()))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("API Key authentication successful for user: {}", apiUser.getLoginId());

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error during API key authentication for key: {}", apiKey, e);
            throw new ApiException(ErrorCode.UNAUTHORIZED, "API Key 인증 처리중 오류가 발생했습니다.");
        }

        filterChain.doFilter(request, response);
    }


    /**
     * API Key 유효 기간 검증 (start_dt ~ end_dt)
     */
    private void validateKeyPeriod(OpenApiAuthKeyEntity keyEntity) {
        java.time.LocalDate today = java.time.LocalDate.now();
        
        // 시작일 체크
        if (keyEntity.getStartDt() != null && today.isBefore(keyEntity.getStartDt())) {
            log.warn("API Key not yet valid. Start date: {}, Today: {}", keyEntity.getStartDt(), today);
            throw new ApiException(ErrorCode.UNAUTHORIZED, "아직 사용할 수 없는 API Key 입니다.");
        }
        
        // 종료일 체크
        if (keyEntity.getEndDt() != null && today.isAfter(keyEntity.getEndDt())) {
            log.warn("API Key expired. End date: {}, Today: {}", keyEntity.getEndDt(), today);
            throw new ApiException(ErrorCode.UNAUTHORIZED, "만료된 API Key 입니다.");
        }
    }
} 