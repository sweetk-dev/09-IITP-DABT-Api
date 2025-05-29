package com.sweetk.iitp.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IITP API")
                        .description("""
                            # IITP API Documentation
                            
                            ## Overview
                            IITP API는 POI(Point of Interest) 데이터를 제공하는 RESTful API입니다.
                            
                            ## Authentication
                            - 모든 API 요청에는 유효한 API Key가 필요합니다.
                            - API Key는 'X-API-Key' 헤더에 포함되어야 합니다.
                            
                            ## Rate Limiting
                            - API 호출은 분당 100회로 제한됩니다.
                            - 제한을 초과하면 429 Too Many Requests 응답이 반환됩니다.
                            """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("IITP Team")
                                .email("contact@iitp.kr")
                                .url("https://iitp.kr"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://iitp.kr/license")))
                .servers(List.of(
                        new Server()
                                .url("https://api.iitp.kr")
                                .description("Production Server"),
                        new Server()
                                .url("https://api-dev.iitp.kr")
                                .description("Development Server")
                ))
                .tags(List.of(
                        new Tag().name("POI").description("POI 관련 API"),
                        new Tag().name("POI V1").description("POI API V1"),
                        new Tag().name("POI V2").description("POI API V2")
                ))
                .components(new Components()
                        .addSecuritySchemes("ApiKeyAuth", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X-API-Key")
                                        .description("API Key for authentication")))
                .addSecurityItem(new SecurityRequirement().addList("ApiKeyAuth"));
    }
} 