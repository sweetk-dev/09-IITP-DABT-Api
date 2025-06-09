package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.basic.BasicHousingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 기초-주거 자립 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_HOUSING)
@RequiredArgsConstructor
@Validated
@Tag(name = "기초-주거 자립 현황 API V1", description = "기초-주거 자립 현황 OpenApi V1")
public class BasicHousingController extends BasicBaseController {
    private final BasicHousingService housingService;


    /*******************************
     * 신규등록 장애인현황
     *******************************/

    @GetMapping("/latest/reg/new")
    @Operation(
            summary = "최근 신규등록 장애인현황 조회",
            description = "최근 신규등록 장애인현황 조회(최대 10년) 예) from(옵션)~(가장 최신연도), default는 가장 최신연도"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewLatest(
            @Parameter(name = "from", description = "(옵션) 검색 시작 연도 (2019 이후 부터 가능, 최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            @Min(value = ApiConstants.Param.START_STAT_YEAR_HOUSING_REG,
                            message = ApiConstants.Param.START_STAT_YEAR_ERR_MSG)
            Integer from,
            HttpServletRequest request) {

        CheckFromYear(request.getRequestURI(), from);

        StatDataRes statDataRes = null;
        try {
            statDataRes = housingService.getHousingRegNewLatest(from);
        }
        catch (Exception e) {
            log.error("최근 신규등록 장애인현황 조회 실패, 에러: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/latest/reg/new/{statYear}")
    @Operation(
            summary = "해당 연도 신규등록 장애인현황 조회",
            description = "해당 연도의 신규등록 장애인현황 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            @Min(value = ApiConstants.Param.START_STAT_YEAR_HOUSING_REG,
                        message = ApiConstants.Param.START_STAT_YEAR_ERR_MSG)
            Integer statYear,
            HttpServletRequest request) {

        StatDataRes statDataRes = null;
        try {
            statDataRes = housingService.getHousingLatestRegNew(statYear);
        }
        catch (Exception e) {
            log.error("최근 신규등록 장애인현황 조회 실패, 에러: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }



    /*******************************
     * 전국 연령별,장애등급별,성별 등록장애인수
     *******************************/
    @GetMapping("/latest/reg/nation")
    @Operation(
            summary = "최근 신규등록 장애인현황 조회",
            description = "최근 신규등록 장애인현황 조회(최대 10년) 예) from(옵션)~(가장 최신연도), default는 가장 최신연도"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewLatest(
            @Parameter(name = "from", description = "(옵션) 검색 시작 연도 (2019 이후 부터 가능, 최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            @Min(value = ApiConstants.Param.START_STAT_YEAR_HOUSING_REG,
                    message = ApiConstants.Param.START_STAT_YEAR_ERR_MSG)
            Integer from,
            HttpServletRequest request) {

        CheckFromYear(request.getRequestURI(), from);

        StatDataRes statDataRes = null;
        try {
            statDataRes = housingService.getHousingLatestRegNation(from);
        }
        catch (Exception e) {
            log.error("최근 신규등록 장애인현황 조회 실패, 에러: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/latest/reg/nat/{statYear}")
    @Operation(
            summary = "해당 연도 신규등록 장애인현황 조회",
            description = "해당 연도의 신규등록 장애인현황 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            @Min(value = ApiConstants.Param.START_STAT_YEAR_HOUSING_REG,
                    message = ApiConstants.Param.START_STAT_YEAR_ERR_MSG)
            Integer statYear,
            HttpServletRequest request) {

        StatDataRes statDataRes = null;
        try {
            statDataRes = housingService.getHousingLatestRegNew(statYear);
        }
        catch (Exception e) {
            log.error("최근 신규등록 장애인현황 조회 실패, 에러: {}", e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    /*******************************
     * 시도별,장애유형별,장애정도별,성별 등록장애인수
     *******************************/



    /*******************************
     * 일상생활 필요 지원 정도
     *******************************/




    /*******************************
     * 주로 지원해주는 사람의 유형
     *******************************/



    /*******************************
     * 일상생활 도와주는 사람(1순위)
     *******************************/





    /*******************************
     * 도움받는 분야
     *******************************/

}
