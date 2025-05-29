package com.sweetk.iitp.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class VersionConfig implements WebMvcConfigurer {

    public static final String VERSION_HEADER = "X-API-Version";
    public static final String DEFAULT_VERSION = "1";

    public void configureHandlerMapping(RequestMappingHandlerMapping handlerMapping) {
        handlerMapping.setCustomCondition(new ApiVersionRequestCondition());
    }
} 