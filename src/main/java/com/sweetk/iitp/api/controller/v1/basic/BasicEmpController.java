package com.sweetk.iitp.api.controller.v1.basic;


import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.basic.StatDataRes;
import com.sweetk.iitp.api.dto.basic.StatInfo;
import com.sweetk.iitp.api.dto.common.ApiResDto;
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
    public ResponseEntity<ApiResDto<StatInfo>> getEmpNatlInfo(
            HttpServletRequest request) {

            StatInfo statinfo = empReadService.getEmpNatlInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/nation/latest")
    @Operation(
            summary = "장애인 근로자 고용현황 조회(최신)",
            description = "장애인 근로자 고용현황. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/nation/{statYear}")
    @Operation(
            summary = "해당 연도의 장애인 근로자 고용현황 조회(연도)",
            description = "해당 연도의 장애인 근로자 고용현황 조회"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    /*******************************
     * 공공기관 장애인고용 현황
     *******************************/
    @GetMapping("/public/info")
    @Operation(
            summary = "공공기관 장애인고용 현황 통계 정보 조회",
            description = "공공기관 장애인고용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto<StatInfo>> getEmpNatlPublicInfo(
            HttpServletRequest request) {

            StatInfo statinfo = empReadService.getEmpNatlPublicInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/public/latest")
    @Operation(
            summary = "공공기관 장애인고용 현황 조회(최신)",
            description = "공공기관 장애인고용 현황. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlPublicLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlPublicLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    @GetMapping("/public/{statYear}")
    @Operation(
            summary = "해당연도 공공기관 장애인고용 현황 조회(연도)",
            description = "공공기관 장애인고용 현황 조회"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlPublicYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlPublicYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    /*******************************
     * 민간기업 장애인고용 현황
     *******************************/
    @GetMapping("/private/info")
    @Operation(
            summary = "민간기업 장애인고용 현황 통계 정보 조회",
            description = "민간기업 장애인고용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto<StatInfo>> getEmpNatlPrivateInfo(
            HttpServletRequest request) {

            StatInfo statinfo = empReadService.getEmpNatlPrivateInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/private/latest")
    @Operation(
            summary = "민간기업 장애인고용 현황 조회(최신)",
            description = "민간기업 장애인고용 현황. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlPrivateLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlPrivateLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/private/{statYear}")
    @Operation(
            summary = "해당연도 민간기업 장애인고용 현황 조회(연도)",
            description = "민간기업 장애인고용 현황 조회"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlPrivateYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlPrivateYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }



    /*******************************
     * 정부부문 장애인고용 현황
     *******************************/
    @GetMapping("/govOrg/info")
    @Operation(
            summary = "정부부문 장애인고용 현황 통계 정보 조회",
            description = "정부부문 장애인고용 현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto<StatInfo>> getEmpNatlGovOrgInfo(
            HttpServletRequest request) {

            StatInfo statinfo = empReadService.getEmpNatlGovOrgInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/govOrg/latest")
    @Operation(
            summary = "정부부문 장애인고용 현황 조회(최신)",
            description = "정부부문 장애인고용 현황. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlGovOrgLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlGovOrgLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/govOrg/{statYear}")
    @Operation(
            summary = "해당 연도의 정부부문 장애인고용 현황 조회(연도)",
            description = "정부부문 장애인고용 현황 조회"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlGovOrgYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlGovOrgYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    /*******************************
     * 장애유형 및 장애정도별 장애인 근로자 고용현황
     *******************************/
    @GetMapping("/disTypeSev/info")
    @Operation(
            summary = "장애유형 및 장애정도별 장애인 근로자 고용현황 통계 정보 조회",
            description = "장애유형 및 장애정도별 장애인 근로자 고용현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto<StatInfo>> getEmpNatlDisTypeSevInfo(
            HttpServletRequest request) {

            StatInfo statinfo = empReadService.getEmpNatlDisTypeSevInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/disTypeSev/latest")
    @Operation(
            summary = "장애유형 및 장애정도별 장애인 근로자 고용현황 조회(최신)",
            description = "장애유형 및 장애정도별 장애인 근로자 고용현황. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlDisTypeSevLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeSevLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/disTypeSev/{statYear}")
    @Operation(
            summary = "해당연도의 장애유형 및 장애정도별 장애인 근로자 고용현황 조회(연도)",
            description = "장애유형 및 장애정도별 장애인 근로자 고용현황 조회"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlDisTypeSevYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeSevYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    /*******************************
     * 장애유형 및 산업별 장애인 근로자 고용현황
     *******************************/
    @GetMapping("/disTypeIndust/info")
    @Operation(
            summary = "장애유형 및 산업별 장애인 근로자 고용현황 통계 정보 조회",
            description = "장애유형 및 산업별 장애인 근로자 고용현황 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto<StatInfo>> getEmpNatlDisTypeIndustInfo(
            HttpServletRequest request) {

            StatInfo statinfo = empReadService.getEmpNatlDisTypeIndustInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/disTypeIndust/latest")
    @Operation(
            summary = "장애유형 및 산업별 장애인 근로자 고용현황 조회(최신)",
            description = "장애유형 및 산업별 장애인 근로자 고용현황. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlDisTypeIndustLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeIndustLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/disTypeIndust/{statYear}")
    @Operation(
            summary = "해당연도의 장애유형 및 산업별 장애인 근로자 고용현황 조회(연도)",
            description = "장애유형 및 산업별 장애인 근로자 고용현황 조회"
    )
    public ResponseEntity<ApiResDto<StatDataRes>> getEmpNatlDisTypeIndustYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = empReadService.getEmpNatlDisTypeIndustYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

}
