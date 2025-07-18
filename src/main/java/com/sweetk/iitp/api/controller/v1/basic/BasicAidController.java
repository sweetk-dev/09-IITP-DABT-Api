package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
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

            StatInfo statinfo = aidReadService.getAidDeviceUsageInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/deviceUsg/latest")
    @Operation(
            summary = "장애인보조기기 사용여부 조회(최신)",
            description = "장애인보조기기 사용여부. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getAidDeviceUsageLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = aidReadService.getAidDeviceUsageLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/deviceUsg/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인보조기기 사용여부 조회(연도)",
            description = "해당 연도의 장애인보조기기 사용여부 조회"
    )
    public ResponseEntity<ApiResDto> getAidDeviceUsageYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = aidReadService.getAidDeviceUsageYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatInfo statinfo = aidReadService.getAidDeviceNeedInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/deviceNeed/latest")
    @Operation(
            summary = "장애인보조기기 필요여부 조회(최신)",
            description = "장애인보조기기 필요여부. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getAidDeviceNeedLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = aidReadService.getAidDeviceNeedLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/deviceNeed/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인보조기기 필요여부 조회(연도)",
            description = "해당 연도의 장애인보조기기 필요여부 조회"
    )
    public ResponseEntity<ApiResDto> getAidDeviceNeedYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = aidReadService.getAidDeviceNeedYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

}
