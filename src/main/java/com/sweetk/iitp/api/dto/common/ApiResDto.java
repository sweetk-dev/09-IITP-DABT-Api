package com.sweetk.iitp.api.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetk.iitp.api.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "공통 응답 포맷")
public class ApiResDto<T> {
    @Schema(description = "성공 여부", example = "true")
    private final boolean success;

    @Schema(description = "응답 데이터")
    private final T data;

    @Schema(description = "응답 코드", example = "200")
    private final HttpStatus status;

    @Schema(description = "에러 정보", implementation = ErrInfoDto.class)
    private final ErrInfoDto error;

    public ApiResDto(boolean success, T data, HttpStatus status, ErrInfoDto error) {
        this.success = success;
        this.data = data;
        this.status = status;
        this.error = error;
    }

    public static <T> ApiResDto<T> success(T data) {
        return new ApiResDto<>(true, data, HttpStatus.OK, null);
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
    // 아래 메서드는 더 이상 사용하지 않도록 주석 처리 또는 deprecated 처리
    /*
    public static <T> ApiResDto<T> error(String code, String message) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(null)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static <T> ApiResDto<T> error(String code, String message, String details) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(null)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    public static <T> ApiResDto<T> error(HttpStatus status, String code, String message) {
        return ApiResDto.<T>builder()
                .success(false)
                .error(null)
                .status(status)
                .build();
    }
    */

    public static <T> ApiResDto<T> error(ErrorCode errorCode) {
        return new ApiResDto<>(false, null, errorCode.getStatus(), ErrInfoDto.of(errorCode, null));
    }

    public static <T> ApiResDto<T> error(ErrorCode errorCode, String detailMessage) {
        return new ApiResDto<>(false, null, errorCode.getStatus(), ErrInfoDto.of(errorCode, detailMessage));
    }
} 