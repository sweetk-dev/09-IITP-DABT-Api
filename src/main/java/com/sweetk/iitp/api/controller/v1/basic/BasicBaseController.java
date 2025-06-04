package com.sweetk.iitp.api.controller.v1.basic;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 기초 공통 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC)
@Tag(name = "기초 데이터 API V1", description = "기초 데이터 OpenAPI V1 ")
public class BasicBaseController {

    /*

    public static <T> ApiResDto<T> BasicApiOk(T data) {
        return ApiResDto.success(data);
    }

    public static <T> ApiResDto<T> BasicApiError(ErrorCode errorCode) {
        return BusinessException.of(errorCode);
    }

    public static <T> ApiResDto<T> BasicApiError(ErrorCode errorCode, String detailMessage) {
        return ErrApiResDto.of(errorCode, detailMessage);
    }
    */

    public void CheckFromYear(String reqURI, Integer fromYear ) {
        ErrorCode errorCode = isStatFromYearOk(fromYear);
        if (errorCode != ErrorCode.SUCCESS) {
            String detailMsg = String.format("from(%s) is invalid (MAx %s Year)", fromYear, ApiConstants.Param.MAX_STAT_YEAR_PERIOD);
            log.error("[{}] :: {}", reqURI, detailMsg);
            throw new BusinessException(errorCode, detailMsg);
        }
    }

    /**
     * 주어진 fromYear 가 현재년도에서 MAX_STAT_YEAR_PERIOD(10) 이내인지 확인
     * @param fromYear
     * @return
     */
    public ErrorCode isStatFromYearOk(Integer fromYear) {
        if (fromYear == null ) {
            return ErrorCode.INVALID_PARAMETER;
        }

        Integer currentYear = LocalDate.now().getYear();
        int diffYear = currentYear - fromYear;

        if(diffYear < 0 || diffYear > ApiConstants.Param.MAX_STAT_YEAR_PERIOD  ) {
            return ErrorCode.INVALID_INPUT_VALUE;
        }

        return ErrorCode.SUCCESS;
    }
} 