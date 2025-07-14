package com.sweetk.iitp.api.config;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.ErrApiResDto;
import com.sweetk.iitp.api.dto.common.ErrInfoDto;
import com.sweetk.iitp.api.exception.ErrorCode;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OpenAPI 문서 생성을 위한 설정 클래스
 * 
 * 이 클래스는 swagger-core를 직접 사용하여 OpenAPI 문서를 생성합니다.
 * springdoc-openapi-starter-webmvc-ui 대신 더 가벼운 의존성으로 구현되었습니다.
 * 
 * ErrorCode enum의 모든 정보는 {@link #customOpenAPI()} 메서드에서 자동으로 처리됩니다.
 * ErrorCode enum이 변경되면 별도의 수동 작업 없이 자동으로 문서에 반영됩니다.
 * 
 * 주요 기능:
 * - ErrorCode enum 값들을 OpenAPI 스키마에 포함
 * - 각 에러 코드의 설명을 스키마의 description에 추가
 * - 공통 응답 형식 (ApiResDto, ErrApiResDto) 정의
 * - x-tagGroups를 통한 API 그룹핑 (Stoplight Elements 지원)
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "IITP Open API Documentation",
                version = "${springdoc.version}",
                description = "IITP Open API 문서입니다."
        )
)
public class SpringDocConfig {
    
    /**
     * OpenAPI 문서 생성을 위한 커스텀 설정을 제공합니다.
     * 
     * 이 메서드는 다음과 같은 작업을 수행합니다:
     * 1. ErrorCode enum의 모든 값을 자동으로 스캔하여 문서에 포함
     * 2. 각 에러 코드의 설명을 스키마의 description에 추가
     * 3. 공통 응답 형식 스키마 정의
     * 4. x-tagGroups를 통한 API 그룹핑 설정
     * 
     * ErrorCode enum이 변경되어도 별도의 수동 작업이 필요하지 않습니다.
     * Arrays.stream(ErrorCode.values())를 통해 현재 정의된 모든 에러 코드를
     * 자동으로 가져와서 문서화합니다.
     */
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

        // ErrorCode enum 정보를 수동으로 스키마에 추가 (ErrInfoDto.code 필드)
        Map<String, Schema> errorInfoProperties = errorInfoSchema.getProperties();
        if (errorInfoProperties != null) {
            Schema<?> codeSchema = errorInfoProperties.get("code");
            if (codeSchema != null) {
                List<String> enumValues = Arrays.stream(ErrorCode.values())
                        .map(e -> String.valueOf(e.getCode()))
                        .collect(Collectors.toList());
                @SuppressWarnings("rawtypes")
                List list = enumValues;
                codeSchema.setEnum(list);

                // description에 전체 에러 코드 목록 추가 (필드 레벨)
                String description = Arrays.stream(ErrorCode.values())
                    .map(e -> e.getCode() + ": " + e.getMessage())
                    .collect(Collectors.joining("\n"));
                codeSchema.setDescription("에러 코드 목록:\n" + description);
            }
        }

        // ErrorCode enum 타입 스키마에 description 추가 (components.schemas.ErrorCode)
        Schema<?> errorCodeSchema = new Schema<>()
                .type("string")
                .name("ErrorCode")
                .example("C000")
                ._enum(Arrays.stream(ErrorCode.values()).map(ErrorCode::getCode).collect(Collectors.toList()));
        String errorCodeDesc = Arrays.stream(ErrorCode.values())
                .map(e -> e.getCode() + ": " + e.getMessage())
                .collect(Collectors.joining("\n"));
        errorCodeSchema.setDescription("에러 코드 목록:\n" + errorCodeDesc);

        OpenAPI openAPI = new OpenAPI()
                .components(new Components()
                        .addSchemas("ErrorInfoDto", errorInfoSchema)
                        .addSchemas("ApiResDto", apiResSchema)
                        .addSchemas("ErrApiResDto", errApiResSchema)
                        .addSchemas("ErrorCode", errorCodeSchema)
                        .addResponses("ErrorApiResponse", new ApiResponse()
                                .description("Error response")
                                .content(new Content().addMediaType(
                                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/ErrApiResDto"))
                                ))
                        )
                );

        return openAPI;
    }
}