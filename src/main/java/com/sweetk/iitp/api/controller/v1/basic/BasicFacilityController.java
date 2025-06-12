package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.basic.BasicFacilityReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기초-편의 시설 제공 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_FACILITY)
@RequiredArgsConstructor
@Tag(name = "기초-편의 시설 제공 현황 API V1", description = "기초-편의 시설 제공 현황 OpenApi V1")
public class BasicFacilityController extends BasicBaseController {

    private final BasicFacilityReadService facilityReadService;

    /*******************************
     * 사회복지시설 이용 현황
     *******************************/
    @GetMapping("/welfareUsg/info")
    @Operation(
            summary = "사회복지시설 이용 현황 통계 정보 조회",
            description = "사회복지시설 이용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getFcltyWelfareUsageInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = facilityReadService.getFcltyWelfareUsageInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/welfareUsg/latest")
    @Operation(
            summary = "사회복지시설 이용 현황 조회",
            description = "사회복지시설 이용 현황. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getFcltyWelfareUsageLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = facilityReadService.getFcltyWelfareUsageLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/welfareUsg/{statYear}")
    @Operation(
            summary = "해당 연도의 사회복지시설 이용 현황  조회",
            description = "해당 연도의 사회복지시설 이용 현황 조회"
    )
    public ResponseEntity<ApiResDto> getFcltyWelfareUsageYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = facilityReadService.getFcltyWelfareUsageYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
