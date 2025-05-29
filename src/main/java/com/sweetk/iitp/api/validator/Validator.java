package com.sweetk.iitp.api.validator;

import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import org.springframework.util.StringUtils;

public abstract class Validator {
    
    protected void validateRequired(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new BusinessException(ErrorCode.REQUIRED_FIELD_MISSING, 
                String.format("Field '%s' is required", fieldName));
        }
    }

    protected void validateLength(String value, String fieldName, int minLength, int maxLength) {
        if (value != null && (value.length() < minLength || value.length() > maxLength)) {
            throw new BusinessException(ErrorCode.INVALID_FORMAT,
                String.format("Field '%s' length must be between %d and %d", fieldName, minLength, maxLength));
        }
    }

    protected void validatePattern(String value, String fieldName, String pattern, String message) {
        if (value != null && !value.matches(pattern)) {
            throw new BusinessException(ErrorCode.INVALID_FORMAT,
                String.format("Field '%s' %s", fieldName, message));
        }
    }

    protected void validateRange(Number value, String fieldName, Number min, Number max) {
        if (value != null && (value.doubleValue() < min.doubleValue() || value.doubleValue() > max.doubleValue())) {
            throw new BusinessException(ErrorCode.INVALID_FORMAT,
                String.format("Field '%s' must be between %s and %s", fieldName, min, max));
        }
    }

    protected void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new BusinessException(ErrorCode.REQUIRED_FIELD_MISSING,
                String.format("Field '%s' cannot be null", fieldName));
        }
    }

    protected void validateCondition(boolean condition, String message) {
        if (!condition) {
            throw new BusinessException(ErrorCode.INVALID_STATE, message);
        }
    }
} 