package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.basic.BasicAidReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기초-보조기기 사용 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_AID)
@RequiredArgsConstructor
@Tag(name = "기초-보조기기 사용 현황 API V1", description = "기초-보조기기 사용 현황 OpenApi V1")
public class BasicAidController extends BasicBaseController {

    private final BasicAidReadService aidReadService;



    /*******************************
     * 장애인보조기기 사용여부
     *******************************/
    @GetMapping("/deviceUsg/info")
    @Operation(
            summary = "장애인보조기기 사용여부 통계 정보 조회",
            description = "장애인보조기기 사용여부 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getAidDeviceUsageInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = aidReadService.getAidDeviceUsageInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/deviceUsg/latest")
    @Operation(
            summary = "장애인보조기기 사용여부 조회",
            description = "장애인보조기기 사용여부. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getAidDeviceUsageLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = aidReadService.getAidDeviceUsageLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deviceUsg/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인보조기기 사용여부 조회",
            description = "해당 연도의 장애인보조기기 사용여부 조회"
    )
    public ResponseEntity<ApiResDto> getAidDeviceUsageYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = aidReadService.getAidDeviceUsageYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 장애인보조기기 필요여부
     *******************************/
    @GetMapping("/deviceNeed/info")
    @Operation(
            summary = "장애인보조기기 필요여부 통계 정보 조회",
            description = "장애인보조기기 필요여부 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getAidDeviceNeedInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = aidReadService.getAidDeviceNeedInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/deviceNeed/latest")
    @Operation(
            summary = "장애인보조기기 필요여부 조회",
            description = "장애인보조기기 필요여부. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getAidDeviceNeedLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = aidReadService.getAidDeviceNeedLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deviceNeed/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인보조기기 필요여부 조회",
            description = "해당 연도의 장애인보조기기 필요여부 조회"
    )
    public ResponseEntity<ApiResDto> getAidDeviceNeedYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = aidReadService.getAidDeviceNeedYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
