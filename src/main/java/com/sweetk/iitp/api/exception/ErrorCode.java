package com.sweetk.iitp.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Success
    SUCCESS(HttpStatus.OK, "S000", "SUCCESS"),

    // Common Errors
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C000", "요청한 parameter이 올바르지 않습니다."),
    NO_MATCHING_DATA (HttpStatus.NO_CONTENT, "C001", "요청한 조건에 맞는 데이터가 없습니다."),//필수 파라메터 누락, null 등
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "입력값이 올바르지 않습니다."),             //사용자 입력 값이 비즈니스 규칙 또는 범위를 위반한 경우
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "지원하지 않는 HTTP 메소드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C004", "요청한 리소스를 찾을 수 없습니다."),
    REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "C005", "필수 입력값이 누락되었습니다."),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "C006", "입력 형식이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C007", "서버 내부 오류가 발생했습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C008", "잘못된 타입의 값이 입력되었습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "C009", "접근이 거부되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C010", "인증이 필요합니다."),

    // Rate Limiting
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "C010", "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),

    // Business Errors
    BUSINESS_EXCEPTION(HttpStatus.BAD_REQUEST, "B001", "비즈니스 로직 처리 중 오류가 발생했습니다."),
    DUPLICATE_ENTITY(HttpStatus.CONFLICT, "B002", "이미 존재하는 데이터입니다."),
    INVALID_STATE(HttpStatus.BAD_REQUEST, "B003", "잘못된 상태입니다."),
    INVALID_VERSION(HttpStatus.BAD_REQUEST, "B004", "잘못된 API 버전입니다."),

    // POI Errors
    POI_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "POI를 찾을 수 없습니다."),
    POI_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "P002", "POI 생성에 실패했습니다."),
    POI_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "P003", "POI 수정에 실패했습니다."),
    POI_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "P004", "POI 삭제에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
} 