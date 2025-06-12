package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.basic.BasicHousingReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final BasicHousingReadService housingReadService;


    /*******************************
     * 신규등록 장애인현황
     *******************************/

    @GetMapping("/reg/new/info")
    @Operation(
            summary = "신규등록 장애인현황 통계 정보 조회",
            description = "신규등록 장애인현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = housingReadService.getHousingRegNewInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/reg/new/latest")
    @Operation(
            summary = "신규등록 장애인현황 조회",
            description = "신규등록 장애인현황 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingRegNewLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reg/new/{statYear}")
    @Operation(
            summary = "해당 연도의  신규등록 장애인현황 조회",
            description = "해당 연도의 신규등록 장애인현황 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingRegNewYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     * 전국 연령별,장애등급별,성별 등록장애인수
     *******************************/
    @GetMapping("/reg/ageSevGen/info")
    @Operation(
            summary = "전국 연령별,장애등급별,성별 등록장애인수 통계 정보 조회",
            description = "전국 연령별,장애등급별,성별 등록장애인수 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegAgeSevGenInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = housingReadService.getHousingRegAgeSevGenInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/reg/ageSevGen/latest")
    @Operation(
            summary = "전국 연령별,장애등급별,성별 등록장애인수 조회",
            description = "전국 연령별,장애등급별,성별 등록장애인수 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingRegAgeSevGenLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingRegAgeSevGenLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reg/ageSevGen/{statYear}")
    @Operation(
            summary = "해당 연도의  전국 연령별,장애등급별,성별 등록장애인수 조회",
            description = "해당 연도의 전국 연령별,장애등급별,성별 등록장애인수 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegAgeSevGenYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingRegAgeSevGenYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 시도별,장애유형별,장애정도별,성별 등록장애인수
     *******************************/
    @GetMapping("/reg/sidoSevGen/latest")
    @Operation(
            summary = "시도별,장애등급별,성별 등록장애인수 조회",
            description = "시도별,장애등급별,성별 등록장애인수 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingRegSidoASevGenLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingRegSidoSevGenLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reg/sidoSevGen/{statYear}")
    @Operation(
            summary = "해당 연도의  시도별,장애등급별,성별 등록장애인수 조회",
            description = "해당 연도의 시도별,장애등급별,성별 등록장애인수 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegSidoSevGenYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingRegSidoSevGenYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     * 일상생활 필요 지원 정도
     *******************************/
    @GetMapping("/life/SuppNeedLvl/info")
    @Operation(
            summary = "일상생활 필요 지원 정도 통계 정보 조회",
            description = "일상생활 필요 지원 정도 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppNeedLvlInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = housingReadService.getHousingLifeSuppNeedLvlInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/life/SuppNeedLvl/latest")
    @Operation(
            summary = "일상생활 필요 지원 정도 조회",
            description = "일상생활 필요 지원 정도 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppNeedLvlLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifeSuppNeedLvlLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/life/SuppNeedLvl/{statYear}")
    @Operation(
            summary = "해당 연도의 일상생활 필요 지원 정도 조회",
            description = "해당 연도의 일상생활 필요 지원 정도 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppNeedLvlYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifeSuppNeedLvlYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     * 주로 지원해주는 사람의 유형
     *******************************/
    @GetMapping("/life/mainCarer/info")
    @Operation(
            summary = "주로 지원해주는 사람의 유형 통계 정보 조회",
            description = "주로 지원해주는 사람의 유형 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeMainCarerInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = housingReadService.getHousingLifeMaincarerInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/life/mainCarer/latest")
    @Operation(
            summary = "주로 지원해주는 사람의 유형  조회",
            description = "주로 지원해주는 사람의 유형 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifeMainCarerLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifeMaincarerLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/life/mainCarer/{statYear}")
    @Operation(
            summary = "해당 연도의 주로 지원해주는 사람의 유형 조회",
            description = "해당 연도의 주로 지원해주는 사람의 유형 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeMainCarerYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifeMaincarerYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     * 일상생활 도와주는 사람(1순위)
     *******************************/

    @GetMapping("/life/primCarer/info")
    @Operation(
            summary = "일상생활 도와주는 사람(1순위) 통계 정보 조회",
            description = "일상생활 도와주는 사람(1순위) 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifePrimCarerInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = housingReadService.getHousingLifePrimcarerInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/life/primCarer/latest")
    @Operation(
            summary = "일상생활 도와주는 사람(1순위)  조회",
            description = "일상생활 도와주는 사람(1순위) 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifePrimCarerLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifePrimcarerLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/life/primCarer/{statYear}")
    @Operation(
            summary = "해당 연도의 일상생활 도와주는 사람(1순위) 조회",
            description = "해당 연도의 일상생활 도와주는 사람(1순위) 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifePrimCarerYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifePrimcarerYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     * 도움받는 분야
     *******************************/

    @GetMapping("/life/suppField/info")
    @Operation(
            summary = "도움받는 분야 통계 정보 조회",
            description = "도움받는 분야 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppFieldInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = housingReadService.getHousingLifeSuppFieldInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/life/suppField/latest")
    @Operation(
            summary = "도움받는 분야  조회",
            description = "도움받는 분야 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppFieldLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifeSuppFieldLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/life/suppField/{statYear}")
    @Operation(
            summary = "해당 연도의 도움받는 분야 조회",
            description = "해당 연도의 도움받는 분야 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppFieldYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = housingReadService.getHousingLifeSuppFieldYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
