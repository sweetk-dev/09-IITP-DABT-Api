package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
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

            StatInfo statinfo = facilityReadService.getFcltyWelfareUsageInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/welfareUsg/latest")
    @Operation(
            summary = "사회복지시설 이용 현황 조회(최신)",
            description = "사회복지시설 이용 현황. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getFcltyWelfareUsageLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = facilityReadService.getFcltyWelfareUsageLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/welfareUsg/{statYear}")
    @Operation(
            summary = "해당 연도의 사회복지시설 이용 현황  조회(연도)",
            description = "해당 연도의 사회복지시설 이용 현황 조회"
    )
    public ResponseEntity<ApiResDto> getFcltyWelfareUsageYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = facilityReadService.getFcltyWelfareUsageYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }
}
