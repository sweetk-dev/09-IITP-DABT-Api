package com.sweetk.iitp.api.config;


import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.ErrApiResDto;
import com.sweetk.iitp.api.dto.common.ErrInfoDto;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "IITP Open API Documentation",
                version = "${springdoc.version}",  // application.yml에서 자동 주입
                description = "IITP Open API 문서입니다."
        )
)
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Schema<?> errorInfoSchema = ModelConverters.getInstance()
                .readAllAsResolvedSchema(ErrInfoDto.class)
                .schema;

        Schema<?> apiResSchema = ModelConverters.getInstance()
                .readAllAsResolvedSchema(ApiResDto.class)
                .schema;

        Schema<?> errApiResSchema = ModelConverters.getInstance()
                .readAllAsResolvedSchema(ErrApiResDto.class)
                .schema;

        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ErrorInfoDto", errorInfoSchema)
                        .addSchemas("ApiResDto", apiResSchema)
                        .addSchemas("ErrApiResDto", errApiResSchema)
                        .addResponses("ErrorApiResponse", new ApiResponse()
                                .description("Error response")
                                .content(new Content().addMediaType(
                                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrApiResDto"))
                                ))
                        )
                );
    }
}