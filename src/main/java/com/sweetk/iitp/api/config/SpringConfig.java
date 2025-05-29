package com.sweetk.iitp.api.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ErrorApiResponseDto", new Schema<>()) // 스키마 등록 (선언만, 정의는 DTO 클래스에서)
                        .addResponses("GenericError", new ApiResponse()
                                .description("공통 에러 응답")
                                .content(new Content().addMediaType(
                                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE, // Spring의 MediaType는 FQCN으로 명시
                                        new io.swagger.v3.oas.models.media.MediaType().schema(
                                                new Schema<>().$ref("#/components/schemas/ErrorApiResponseDto")
                                        )
                                ))
                        )
                )
                .info(new Info()
                        .title("My API")
                        .version("1.0")
                        .description("API 명세서")
                );
    }
}
