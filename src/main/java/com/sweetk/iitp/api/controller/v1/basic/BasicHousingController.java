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
            StatInfo statinfo = housingReadService.getHousingRegNewInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/reg/new/latest")
    @Operation(
            summary = "신규등록 장애인현황 조회(최신)",
            description = "신규등록 장애인현황 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewLatest(
            @Parameter(name = "from", description = "(옵션) 통계 조회 시작 연도  (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingRegNewLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/reg/new/{statYear}")
    @Operation(
            summary = "해당 연도의  신규등록 장애인현황 조회(연도)",
            description = "해당 연도의 신규등록 장애인현황 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegNewYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingRegNewYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatInfo statinfo = housingReadService.getHousingRegAgeSevGenInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/reg/ageSevGen/latest")
    @Operation(
            summary = "전국 연령별,장애등급별,성별 등록장애인수 조회(최신)",
            description = "전국 연령별,장애등급별,성별 등록장애인수 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingRegAgeSevGenLatest(
            @Parameter(name = "from", description = "(옵션) 통계 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingRegAgeSevGenLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/reg/ageSevGen/{statYear}")
    @Operation(
            summary = "해당 연도의  전국 연령별,장애등급별,성별 등록장애인수 조회(연도)",
            description = "해당 연도의 전국 연령별,장애등급별,성별 등록장애인수 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegAgeSevGenYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingRegAgeSevGenYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    /*******************************
     * 시도별,장애유형별,장애정도별,성별 등록장애인수
     *******************************/
    @GetMapping("/reg/sidoSevGen/info")
    @Operation(
            summary = "시도별,장애등급별,성별 등록장애인수 통계 정보 조회",
            description = "시도별,장애등급별,성별 등록장애인수 통계 정보 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegSidoSevGenInfo(
            HttpServletRequest request) {

        StatInfo statinfo = housingReadService.getHousingRegSidoSevGenInfo();
        return ResponseEntity.ok(ApiResDto.success(statinfo));
    }

    @GetMapping("/reg/sidoSevGen/latest")
    @Operation(
            summary = "시도별,장애등급별,성별 등록장애인수 조회(최신)",
            description = "시도별,장애등급별,성별 등록장애인수 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingRegSidoASevGenLatest(
            @Parameter(name = "from", description = "(옵션) 통계 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingRegSidoSevGenLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/reg/sidoSevGen/{statYear}")
    @Operation(
            summary = "해당 연도의  시도별,장애등급별,성별 등록장애인수 조회(연도)",
            description = "해당 연도의 시도별,장애등급별,성별 등록장애인수 조회"
    )
    public ResponseEntity<ApiResDto> getHousingRegSidoSevGenYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingRegSidoSevGenYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatInfo statinfo = housingReadService.getHousingLifeSuppNeedLvlInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/life/SuppNeedLvl/latest")
    @Operation(
            summary = "일상생활 필요 지원 정도 조회(최신)",
            description = "일상생활 필요 지원 정도 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppNeedLvlLatest(
            @Parameter(name = "from", description = "(옵션) 통계 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifeSuppNeedLvlLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    @GetMapping("/life/SuppNeedLvl/{statYear}")
    @Operation(
            summary = "해당 연도의 일상생활 필요 지원 정도 조회(연도)",
            description = "해당 연도의 일상생활 필요 지원 정도 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppNeedLvlYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifeSuppNeedLvlYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatInfo statinfo = housingReadService.getHousingLifeMaincarerInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/life/mainCarer/latest")
    @Operation(
            summary = "주로 지원해주는 사람의 유형  조회(최신)",
            description = "주로 지원해주는 사람의 유형 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifeMainCarerLatest(
            @Parameter(name = "from", description = "(옵션) 통계 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifeMaincarerLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    @GetMapping("/life/mainCarer/{statYear}")
    @Operation(
            summary = "해당 연도의 주로 지원해주는 사람의 유형 조회(연도)",
            description = "해당 연도의 주로 지원해주는 사람의 유형 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeMainCarerYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifeMaincarerYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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
            summary = "일상생활 도와주는 사람(1순위)  조회(최신)",
            description = "일상생활 도와주는 사람(1순위) 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifePrimCarerLatest(
            @Parameter(name = "from", description = "(옵션) 통계 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifePrimcarerLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

    @GetMapping("/life/primCarer/{statYear}")
    @Operation(
            summary = "해당 연도의 일상생활 도와주는 사람(1순위) 조회(연도)",
            description = "해당 연도의 일상생활 도와주는 사람(1순위) 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifePrimCarerYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifePrimcarerYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
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

            StatInfo statinfo = housingReadService.getHousingLifeSuppFieldInfo();
            return ResponseEntity.ok(ApiResDto.success(statinfo));
    }


    @GetMapping("/life/suppField/latest")
    @Operation(
            summary = "도움받는 분야  조회(최신)",
            description = "도움받는 분야 조회. (최대 10년) 예) from(옵션)~to(옵션), default는 최종 3개년"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppFieldLatest(
            @Parameter(name = "from", description = "(옵션) 통계 시작 연도 (최대: 10년 조회)", example = "2019")
            @RequestParam(required = false)
            Integer from,
            @Parameter(name = "to", description = "(옵션) 통계 조회 끝 연도  (최대: 10년 조회)", example = "2022")
            @RequestParam(required = false)
            Integer to,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifeSuppFieldLatest(from, to);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }


    @GetMapping("/life/suppField/{statYear}")
    @Operation(
            summary = "해당 연도의 도움받는 분야 조회(연도)",
            description = "해당 연도의 도움받는 분야 조회"
    )
    public ResponseEntity<ApiResDto> getHousingLifeSuppFieldYear(
            @Parameter(name = "statYear", description = "통계 연도", required = true, example = "2024")
            @PathVariable
            Integer statYear,
            HttpServletRequest request) {

            StatDataRes statDataRes = housingReadService.getHousingLifeSuppFieldYear(statYear);
            return ResponseEntity.ok(ApiResDto.success(statDataRes));
    }

}
