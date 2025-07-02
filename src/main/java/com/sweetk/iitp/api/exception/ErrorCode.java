package com.sweetk.iitp.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 에러 코드 enum
 * 
 * 이 enum에 정의된 모든 에러 코드는 OpenAPI 문서에 자동으로 포함됩니다.
 * 관련 설정은 {@link com.sweetk.iitp.api.config.SpringDocConfig#customOpenAPI()} 메서드에서 처리됩니다.
 * 
 * 새로운 에러 코드를 추가할 때 특별한 추가 작업은 필요하지 않습니다.
 * enum에 새 항목을 추가하면 자동으로 OpenAPI 문서에 반영됩니다.
 */
@Getter
@Schema(description = "에러 코드", enumAsRef = true)
public enum ErrorCode {

    // Success
    @Schema(name = "S000", description = "SUCCESS")
    SUCCESS(HttpStatus.OK, "S000", "SUCCESS"),

    // Common Errors
    @Schema(name = "C000", description = "요청한 parameter이 올바르지 않습니다.")
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C000", "요청한 parameter이 올바르지 않습니다."),
    @Schema(name = "C001", description = "요청한 조건에 맞는 데이터가 없습니다.")
    NO_MATCHING_DATA (HttpStatus.NO_CONTENT, "C001", "요청한 조건에 맞는 데이터가 없습니다."),//필수 파라메터 누락, null 등
    @Schema(name = "C002", description = "입력값이 올바르지 않습니다.")
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "입력값이 올바르지 않습니다."),             //사용자 입력 값이 비즈니스 규칙 또는 범위를 위반한 경우
    @Schema(name = "C003", description = "지원하지 않는 HTTP 메소드입니다.")
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "지원하지 않는 HTTP 메소드입니다."),
    @Schema(name = "C004", description = "요청한 리소스를 찾을 수 없습니다.")
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "요청한 리소스를 찾을 수 없습니다."),
    @Schema(name = "C005", description = "필수 입력값이 누락되었습니다.")
    REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "C005", "필수 입력값이 누락되었습니다."),
    @Schema(name = "C006", description = "입력 형식이 올바르지 않습니다.")
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "C006", "입력 형식이 올바르지 않습니다."),
    @Schema(name = "C007", description = "서버 내부 오류가 발생했습니다.")
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C007", "서버 내부 오류가 발생했습니다."),
    @Schema(name = "C008", description = "잘못된 타입의 값이 입력되었습니다.")
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C008", "잘못된 타입의 값이 입력되었습니다."),
    @Schema(name = "C009", description = "접근이 거부되었습니다.")
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C009", "접근이 거부되었습니다."),
    @Schema(name = "C010", description = "인증이 필요합니다.")
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C010", "인증이 필요합니다."),
    @Schema(name = "C011", description = "요청이 너무 많습니다. 잠시 후 다시 시도해주세요.")
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "C011", "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),

    // Business Errors
    @Schema(name = "B001", description = "비즈니스 로직 처리 중 오류가 발생했습니다.")
    BUSINESS_EXCEPTION(HttpStatus.BAD_REQUEST, "B001", "비즈니스 로직 처리 중 오류가 발생했습니다."),
    @Schema(name = "B002", description = "이미 존재하는 데이터입니다.")
    DUPLICATE_ENTITY(HttpStatus.CONFLICT, "B002", "이미 존재하는 데이터입니다."),
    @Schema(name = "B003", description = "잘못된 상태입니다.")
    INVALID_STATE(HttpStatus.BAD_REQUEST, "B003", "잘못된 상태입니다."),
    @Schema(name = "B004", description = "잘못된 API 버전입니다.")
    INVALID_VERSION(HttpStatus.BAD_REQUEST, "B004", "잘못된 API 버전입니다."),

    // POI Errors
    @Schema(name = "P001", description = "POI를 찾을 수 없습니다.")
    POI_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "POI를 찾을 수 없습니다."),
    @Schema(name = "P002", description = "POI 생성에 실패했습니다.")
    POI_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "P002", "POI 생성에 실패했습니다."),
    @Schema(name = "P003", description = "POI 수정에 실패했습니다.")
    POI_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "P003", "POI 수정에 실패했습니다."),
    @Schema(name = "P004", description = "POI 삭제에 실패했습니다.")
    POI_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "P004", "POI 삭제에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
} 