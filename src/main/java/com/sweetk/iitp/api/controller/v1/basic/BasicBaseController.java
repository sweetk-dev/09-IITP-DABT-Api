package com.sweetk.iitp.api.controller.v1.basic;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC)
@Tag(name = "기초 데이터 API V1", description = "기초 데이터 OpenAPI V1 ")
public class BasicBaseController {

    public static <T> ApiResDto<T> BasicApiOk(T data) {
        return ApiResDto.success(data);
    }

    public static <T> ApiResDto<T> BasicApiError(ErrorCode errorCode) {
        return ApiResDto.error(errorCode);
    }

    public static <T> ApiResDto<T> BasicApiError(ErrorCode errorCode, String detailMessage) {
        return ApiResDto.error(errorCode, detailMessage);
    }

} 