package com.sweetk.iitp.api.exception;

import com.sweetk.iitp.api.dto.common.ErrApiResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.security.access.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrApiResDto> handleApiException(ApiException e) {
        log.error("API Exception: code: {}, Message: {}, Detail: {}",
                e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getDetailMessage());

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrApiResDto.of(e.getErrorCode(), e.getDetailMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrApiResDto> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access Denied: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrApiResDto.of(ErrorCode.ACCESS_DENIED));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrApiResDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method Argument Not Valid Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrApiResDto.of(ErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrApiResDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HTTP Message Not Readable Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrApiResDto.of(ErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrApiResDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("Method Argument Type Mismatch Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrApiResDto.of(ErrorCode.INVALID_TYPE_VALUE));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrApiResDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HTTP Request Method Not Supported Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ErrApiResDto.of(ErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrApiResDto> handleBindException(BindException e) {
        log.error("Bind Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrApiResDto.of(ErrorCode.INVALID_INPUT_VALUE));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrApiResDto> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        log.warn("BusinessException 발생 - Code: {}, Message: {}, Detail: {}",
                errorCode.getCode(), errorCode.getMessage(), ex.getDetailMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrApiResDto.of(errorCode, ex.getDetailMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrApiResDto> handleException(Exception e) {
        log.error("Unexpected Exception: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrApiResDto.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
