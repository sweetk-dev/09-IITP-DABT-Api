package com.sweetk.iitp.api.interceptor;

import com.sweetk.iitp.api.exception.ApiException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.util.EncryptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthInterceptor implements HandlerInterceptor {

    private final ApiKeyRepository apiKeyRepository;
    private final EncryptionUtil encryptionUtil;
    private static final String API_KEY_HEADER = "X-API-Key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String apiKey = request.getHeader(API_KEY_HEADER);

        if (apiKey == null || apiKey.isBlank()) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "API Key is required");
        }

        // Get all active API keys
        return apiKeyRepository.findAll().stream()
                .filter(ApiKeyEntity::isActive)
                .anyMatch(key -> encryptionUtil.verifyApiKey(apiKey, key.getEncryptedKey()));
    }
} 
