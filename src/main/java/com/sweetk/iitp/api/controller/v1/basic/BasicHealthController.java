package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.basic.BasicHealthReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기초-건강 관리 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_health)
@RequiredArgsConstructor
@Tag(name = "기초-건강 관리 현황 API V1", description = "기초-건강 관리 현황 OpenApi V1")
public class BasicHealthController extends BasicBaseController {

    private final BasicHealthReadService healthReadService;


    /*******************************
     * 장애인 의료이용 현황
     *******************************/
    @GetMapping("/medicalUsg/info")
    @Operation(
            summary = "장애인 의료이용 현황 통계 정보 조회",
            description = "장애인 의료이용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHealthMedicalUsageInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = healthReadService.getHealthMedicalUsageInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/medicalUsg/latest")
    @Operation(
            summary = "장애인 의료이용 현황 조회",
            description = "장애인 의료이용 현황 조회. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHealthMedicalUsageLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthMedicalUsageLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/medicalUsg/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인 의료이용 현황 조회",
            description = "해당 연도의 장애인 의료이용 현황 조회"
    )
    public ResponseEntity<ApiResDto> getHealthMedicalUsageYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthMedicalUsageYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 장애인 장애유형별 다빈도질환별 진료비현황: 소분류
     *******************************/
    @GetMapping("/disCostSub/info")
    @Operation(
            summary = "장애인 장애유형별 다빈도질환별 진료비현황: 소분류 통계 정보 조회",
            description = "장애인 장애유형별 다빈도질환별 진료비현황: 소분류 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHealthDiseaseCostSubInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = healthReadService.getHealthDiseaseCostSubInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/disCostSub/latest")
    @Operation(
            summary = "장애인 장애유형별 다빈도질환별 진료비현황: 소분류 조회",
            description = "장애인 장애유형별 다빈도질환별 진료비현황: 소분류 조회. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHealthDiseaseCostSubLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthDiseaseCostSubLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/disCostSub/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인 장애유형별 다빈도질환별 진료비현황: 소분류 조회",
            description = "해당 연도의 장애인 장애유형별 다빈도질환별 진료비현황: 소분류 조회"
    )
    public ResponseEntity<ApiResDto> getHealthDiseaseCostSubYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthDiseaseCostSubYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 장애인 생활체육 실행 유형
     *******************************/
    @GetMapping("/sportExecType/info")
    @Operation(
            summary = "장애인 생활체육 실행 유형 통계 정보 조회",
            description = "장애인 생활체육 실행 유형 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHealthSportExecTypeInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = healthReadService.getHealthSportExecTypeInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/sportExecType/latest")
    @Operation(
            summary = "장애인 생활체육 실행 유형 조회",
            description = "장애인 생활체육 실행 유형 조회. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHealthSportExecTypeLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthSportExecTypeLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sportExecType/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인 생활체육 실행 유형 조회",
            description = "해당 연도의 장애인 생활체육 실행 유형 조회"
    )
    public ResponseEntity<ApiResDto> getHealthSportExecTypeYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthSportExecTypeYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 운동 시 가장 도움이 되는 지원 사항
     *******************************/
    @GetMapping("/exrcBestAid/info")
    @Operation(
            summary = "운동 시 가장 도움이 되는 지원 사항 통계 정보 조회",
            description = "운동 시 가장 도움이 되는 지원 사항 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHealthExrcBestAidInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = healthReadService.getHealthExrcBestAidInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/exrcBestAid/latest")
    @Operation(
            summary = "운동 시 가장 도움이 되는 지원 사항 조회",
            description = "운동 시 가장 도움이 되는 지원 사항. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHealthExrcBestAidLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthExrcBestAidLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exrcBestAid/{statYear}")
    @Operation(
            summary = "해당 연도의 운동 시 가장 도움이 되는 지원 사항 조회",
            description = "해당 연도의 운동 시 가장 도움이 되는 지원 사항 조회"
    )
    public ResponseEntity<ApiResDto> getHealthExrcBestAidYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = healthReadService.getHealthExrcBestAidYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



}
