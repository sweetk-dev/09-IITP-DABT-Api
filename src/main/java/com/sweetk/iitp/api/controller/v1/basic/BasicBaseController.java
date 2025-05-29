package com.sweetk.iitp.api.controller.v1.basic;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC)
@Tag(name = "Basic Operations V1", description = "Basic API operations and utilities")
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