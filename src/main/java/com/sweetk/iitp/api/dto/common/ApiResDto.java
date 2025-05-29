package com.sweetk.iitp.api.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetk.iitp.api.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "공통 응답 포맷")
public class ApiResDto<T> {
    @Schema(description = "성공 여부", example = "true")
    private final boolean success;

    @Schema(description = "응답 데이터")
    private final T data;

    @Schema(description = "응답 코드", example = "200")
    private final HttpStatus status;

    @Schema(description = "에러 정보", implementation = ErrorInfo.class)
    private final ErrorInfo error;



    public static <T> ApiResDto<T> success(T data) {
        return ApiResDto.<T>builder()
                .success(true)
                .data(data)
                .status(HttpStatus.OK)
                .build();
    }
/*
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .status(HttpStatus.OK)
                .build();
    }
*/
    public static <T> ApiResDto<T> error(String code, String message) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(ErrorInfo.builder()
                        .code(code)
                        .message(message)
                        .build())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static <T> ApiResDto<T> error(String code, String message, String details) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(ErrorInfo.builder()
                        .code(code)
                        .message(message)
                        .details(details)
                        .build())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static <T> ApiResDto<T> error(HttpStatus status, String code, String message) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(ErrorInfo.builder()
                        .code(code)
                        .message(message)
                        .build())
                .status(status)
                .build();
    }


    public static <T> ApiResDto<T> error(ErrorCode errorCode) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(ErrorInfo.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build())
                .status(errorCode.getStatus()
                )
                .build();
    }

    public static <T> ApiResDto<T> error(ErrorCode errorCode, String detailMessage) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(ErrorInfo.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .details(detailMessage)
                        .build())
                .status(errorCode.getStatus()
                )
                .build();
    }
} 