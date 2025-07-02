package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
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

            StatInfo statinfo = socialReadService.getSocialParticFreqInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/social/particFreq/latest")
    @Operation(
            summary = "장애인의 사회 참여 조회",
            description = "장애인의 사회 참여 조회. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getSocialParticFreqLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = socialReadService.getSocialParticFreqLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatDataRes statDataRes = socialReadService.getSocialParticFreqYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatInfo statinfo = socialReadService.getSocialContactCntfreqInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/social/contractFreq/latest")
    @Operation(
            summary = "가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도 조회",
            description = "가까이 지내는 친구, 이웃, 지인 수 및 만남 빈도. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getSocialContactCntfreqLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = socialReadService.getSocialContactCntfreqLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatDataRes statDataRes = socialReadService.getSocialContactCntfreqYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

}
