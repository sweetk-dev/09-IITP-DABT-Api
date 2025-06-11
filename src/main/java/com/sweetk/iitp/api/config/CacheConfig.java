package com.sweetk.iitp.api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.DAYS)     // 기본 TTL 24시간
            .expireAfterAccess(1, TimeUnit.DAYS)    // 24시간 동안 접근이 없으면 만료
            .maximumSize(1000)                      // 최대 1000개 항목
            .recordStats()                          // 통계 기록
            .removalListener((key, value, cause) -> // 제거 이벤트 리스너
                log.info("Cache entry removed: {} - {} - {}", 
                    key, 
                    value,
                    cause))
        );

        return cacheManager;
    }
} 