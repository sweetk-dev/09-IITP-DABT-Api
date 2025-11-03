package com.sweetk.iitp.api.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@ConditionalOnProperty(name = "management.endpoints.web.exposure.include", havingValue = "health,info")
public class ActuatorConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    @ConditionalOnProperty(name = "management.endpoints.web.exposure.include", havingValue = "health,info")
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    @ConditionalOnProperty(name = "management.endpoints.web.exposure.include", havingValue = "health,info")
    public MeterRegistry meterRegistry() {
        CompositeMeterRegistry registry = new CompositeMeterRegistry();
        registry.config()
            .commonTags("application", applicationName)
            // Actuator 엔드포인트 메트릭 제외
            .meterFilter(MeterFilter.deny(id -> {
                String uri = id.getTag("uri");
                return uri != null && uri.startsWith("/actuator");
            }))
            // 불필요한 JVM 메트릭 제외
            .meterFilter(MeterFilter.deny(id -> {
                String name = id.getName();
                return name != null && (
                    name.startsWith("jvm.gc") ||
                    name.startsWith("jvm.buffer") ||
                    name.startsWith("process.cpu")
                );
            }));
        return registry;
    }
}