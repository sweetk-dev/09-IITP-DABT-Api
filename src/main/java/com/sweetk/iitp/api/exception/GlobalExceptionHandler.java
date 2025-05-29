package com.sweetk.iitp.api.exception;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResDto<Void>> handleApiException(ApiException e) {
        log.error("API Exception: code: {}, Message: {}, Detail: {}",
                e.getErrorCode().getCode(),  e.getErrorCode().getMessage(), e.getDetailMessage());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResDto.error(e.getErrorCode(), e.getDetailMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResDto<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access Denied: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResDto.error(ErrorCode.ACCESS_DENIED));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResDto<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method Argument Not Valid Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResDto.error(ErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResDto<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HTTP Message Not Readable Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResDto.error(ErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResDto<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("Method Argument Type Mismatch Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResDto.error(ErrorCode.INVALID_TYPE_VALUE));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResDto<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HTTP Request Method Not Supported Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResDto.error(ErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResDto<Void>> handleBindException(BindException e) {
        log.error("Bind Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResDto.error(ErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResDto<?>> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        // 로깅: detailMessage까지 출력
        log.warn("BusinessException 발생 - Code: {}, Message: {}, Detail: {}",
                errorCode.getCode(), errorCode.getMessage(), ex.getDetailMessage());

        // 클라이언트 응답은 공통 메시지 기반 (원한다면 detailMessage도 포함 가능)
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResDto.error(errorCode, ex.getDetailMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResDto<Void>> handleException(Exception e) {
        log.error("Unexpected Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResDto.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }
} 