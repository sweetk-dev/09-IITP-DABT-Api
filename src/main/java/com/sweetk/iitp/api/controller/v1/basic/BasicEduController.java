package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.service.basic.BasicEduReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 기초-진로 교육 현황 API Controller
 */
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_BASIC_EDU)
@RequiredArgsConstructor
@Tag(name = "기초-진로 교육 현황 API V1", description = "기초-진로 교육 현황 OpenApi V1")
public class BasicEduController extends BasicBaseController {

    private final BasicEduReadService eduReadService;


    /*******************************
     * 장애인 진로 및 직업교육 실시 여부
     *******************************/
    @GetMapping("/vocaExec/info")
    @Operation(
            summary = "장애인 진로 및 직업교육 실시 여부 통계 정보 조회",
            description = "장애인 진로 및 직업교육 실시 여부 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEduVocaExecInfo(
            HttpServletRequest request) {

            StatInfo statinfo = eduReadService.getEduVocaExecInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/vocaExec/latest")
    @Operation(
            summary = "장애인 진로 및 직업교육 실시 여부 조회",
            description = "장애인 진로 및 직업교육 실시 여부. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEduVocaExecLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = eduReadService.getEduVocaExecLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/vocaExec/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인 진로 및 직업교육 실시 여부 조회",
            description = "해당 연도의 장애인 진로 및 직업교육 실시 여부 조회"
    )
    public ResponseEntity<ApiResDto> getEduVocaExecYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = eduReadService.getEduVocaExecYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }




    /*******************************
     * 장애인 진로 및 직업교육 운영 방법
     *******************************/
    @GetMapping("/vocaExecWay/info")
    @Operation(
            summary = "장애인 진로 및 직업교육 운영 방법 통계 정보 조회",
            description = "장애인 진로 및 직업교육 운영 방법 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getEduVocaExecWayInfo(
            HttpServletRequest request) {
            StatInfo statinfo = eduReadService.getEduVocaExecWayInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/vocaExecWay/latest")
    @Operation(
            summary = "장애인 진로 및 직업교육 운영 방법 조회",
            description = "장애인 진로 및 직업교육 운영 방법. (최대 10년) 예) from(옵션)~(최종 연도), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getEduVocaExecWayLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = eduReadService.getEduVocaExecWayLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/vocaExecWay/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인 진로 및 직업교육 운영 방법 조회",
            description = "해당 연도의 장애인 진로 및 직업교육 운영 방법 조회"
    )
    public ResponseEntity<ApiResDto> getEduVocaExecWayYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = eduReadService.getEduVocaExecWayYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


}
