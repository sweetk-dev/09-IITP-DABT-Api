package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.basic.BasicEmpReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기초-고용 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_EMP)
@RequiredArgsConstructor
@Tag(name = "기초-고용 현황 API V1", description = "기초-고용 현황 OpenApi V1")
public class BasicEmpController extends BasicBaseController {

    private final BasicEmpReadService empReadService;



    /*******************************
     * 장애인 근로자 고용현황
     *******************************/
    @GetMapping("/nation/info")
    @Operation(
            summary = "장애인 근로자 고용현황 통계 정보 조회",
            description = "장애인 근로자 고용현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = empReadService.getEmpNatlInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/nation/latest")
    @Operation(
            summary = "장애인 근로자 고용현황 조회",
            description = "장애인 근로자 고용현황. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEmpNatlLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nation/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인 근로자 고용현황 조회",
            description = "해당 연도의 장애인 근로자 고용현황 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 공공기관 장애인고용 현황
     *******************************/
    @GetMapping("/public/info")
    @Operation(
            summary = "공공기관 장애인고용 현황 통계 정보 조회",
            description = "공공기관 장애인고용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlPublicInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = empReadService.getEmpNatlPublicInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/public/latest")
    @Operation(
            summary = "공공기관 장애인고용 현황 사항 조회",
            description = "공공기관 장애인고용 현황. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEmpNatlPublicLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlPublicLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/public/{statYear}")
    @Operation(
            summary = "공공기관 장애인고용 현황 조회",
            description = "공공기관 장애인고용 현황 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlPublicYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlPublicYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 민간기업 장애인고용 현황
     *******************************/
    @GetMapping("/private/info")
    @Operation(
            summary = "민간기업 장애인고용 현황 통계 정보 조회",
            description = "민간기업 장애인고용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlPrivateInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = empReadService.getEmpNatlPrivateInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/private/latest")
    @Operation(
            summary = "민간기업 장애인고용 현황 조회",
            description = "민간기업 장애인고용 현황. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEmpNatlPrivateLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlPrivateLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/private/{statYear}")
    @Operation(
            summary = "민간기업 장애인고용 현황 조회",
            description = "민간기업 장애인고용 현황 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlPrivateYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlPrivateYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     * 정부부문 장애인고용 현황
     *******************************/
    @GetMapping("/govOrg/info")
    @Operation(
            summary = "정부부문 장애인고용 현황 통계 정보 조회",
            description = "정부부문 장애인고용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlGovOrgInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = empReadService.getEmpNatlGovOrgInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/govOrg/latest")
    @Operation(
            summary = "정부부문 장애인고용 현황 조회",
            description = "정부부문 장애인고용 현황. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEmpNatlGovOrgLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlGovOrgLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/govOrg/{statYear}")
    @Operation(
            summary = "정부부문 장애인고용 현황 조회",
            description = "정부부문 장애인고용 현황 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlGovOrgYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlGovOrgYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 장애유형 및 장애정도별 장애인 근로자 고용현황
     *******************************/
    @GetMapping("/disTypeSev/info")
    @Operation(
            summary = "장애유형 및 장애정도별 장애인 근로자 고용현황 통계 정보 조회",
            description = "장애유형 및 장애정도별 장애인 근로자 고용현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlDisTypeSevInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = empReadService.getEmpNatlDisTypeSevInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/disTypeSev/latest")
    @Operation(
            summary = "장애유형 및 장애정도별 장애인 근로자 고용현황 조회",
            description = "장애유형 및 장애정도별 장애인 근로자 고용현황. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEmpNatlDisTypeSevLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeSevLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/disTypeSev/{statYear}")
    @Operation(
            summary = "장애유형 및 장애정도별 장애인 근로자 고용현황 조회",
            description = "장애유형 및 장애정도별 장애인 근로자 고용현황 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlDisTypeSevYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeSevYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 장애유형 및 산업별 장애인 근로자 고용현황
     *******************************/
    @GetMapping("/disTypeIndust/info")
    @Operation(
            summary = "장애유형 및 산업별 장애인 근로자 고용현황 통계 정보 조회",
            description = "장애유형 및 산업별 장애인 근로자 고용현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlDisTypeIndustInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = empReadService.getEmpNatlDisTypeIndustInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/disTypeIndust/latest")
    @Operation(
            summary = "장애유형 및 산업별 장애인 근로자 고용현황 조회",
            description = "장애유형 및 산업별 장애인 근로자 고용현황. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEmpNatlDisTypeIndustLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeIndustLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/disTypeIndust/{statYear}")
    @Operation(
            summary = "장애유형 및 산업별 장애인 근로자 고용현황 조회",
            description = "장애유형 및 산업별 장애인 근로자 고용현황 조회"
    )
    public ResponseEntity<ApiResDto> getEmpNatlDisTypeIndustYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeIndustYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
