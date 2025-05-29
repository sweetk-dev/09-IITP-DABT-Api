package com.sweetk.iitp.api.interceptor;

import com.sweetk.iitp.api.config.RateLimitConfig;
import com.sweetk.iitp.api.exception.ApiException;
import com.sweetk.iitp.api.exception.ErrorCode;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitConfig rateLimitConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimitConfig.resolveBucket(ip);

        if (bucket.tryConsume(1)) {
            return true;
        }

        throw new ApiException(ErrorCode.TOO_MANY_REQUESTS.getCode(), "Too many requests");
    }
} 