package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.basic.BasicSocialReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기초-사회망 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_SOCIAL)
@RequiredArgsConstructor
@Tag(name = "기초-사회망 현황 API V1", description = "기초-사회망 현황 OpenApi V1")
public class BasicSocialController extends BasicBaseController {
    private final BasicSocialReadService socialReadService;



    /*******************************
     * 장애인의 사회 참여
     *******************************/
    @GetMapping("/social/particFreq/info")
    @Operation(
            summary = "장애인의 사회 참여 통계 정보 조회",
            description = "장애인의 사회 참여 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getSocialParticFreqInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = socialReadService.getSocialParticFreqInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/social/particFreq/latest")
    @Operation(
            summary = "장애인의 사회 참여 조회",
            description = "장애인의 사회 참여 조회. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getSocialParticFreqLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = socialReadService.getSocialParticFreqLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/social/particFreq/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인의 사회 참여 조회",
            description = "해당 연도의 장애인의 사회 참여 조회"
    )
    public ResponseEntity<ApiResDto> getSocialParticFreqYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = socialReadService.getSocialParticFreqYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     * 가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도
     *******************************/
    @GetMapping("/social/contractFreq/info")
    @Operation(
            summary = "가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도 통계 정보 조회",
            description = "가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getSocialContactCntfreqInfo(
            HttpServletRequest request) {

        try {
            StatInfo statinfo = socialReadService.getSocialContactCntfreqInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/social/contractFreq/latest")
    @Operation(
            summary = "가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도 조회",
            description = "가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도. (최대 10년) 예) fromYear(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getSocialContactCntfreqLatest(
            @Parameter(name = "fromYear", description = "(옵션) 검색 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer fromYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = socialReadService.getSocialContactCntfreqLatest(fromYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/social/contractFreq/{statYear}")
    @Operation(
            summary = "해당 연도의 가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도 조회",
            description = "해당 연도의 가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도 조회"
    )
    public ResponseEntity<ApiResDto> getSocialContactCntfreqYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

        try {
            StatDataRes statDataRes = socialReadService.getSocialContactCntfreqYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
        }
        catch (Exception e) {
            log.error("{} Fail,Error: {}", request.getRequestURI(), e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
