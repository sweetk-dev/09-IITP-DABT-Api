package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.emp.*;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.emp.EmpDisReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.sweetk.iitp.api.constant.ApiConstants.ApiPath.API_V1_EMP;


/**
 *  나라 HR - 정적 데이터 15종
 */
@Slf4j
@RestController
@RequestMapping(API_V1_EMP)
@RequiredArgsConstructor
@Tag(name = "장애인 고용 관련 데이터 API V1", description = "장애인 고용 관련 데이터 OpenApi V1")
public class EmpDisController {
    private final EmpDisReadService readService;

    /**
     * 판 기준일(baseDt) 파라미터 설명 — 연도 컬럼이 없어 과거 판이 함께 보관되는 9개 API 공통.
     * (연도 컬럼이 있는 5개 API 는 연도마다 한 판이라 이 파라미터를 받지 않는다)
     */
    static final String BASE_DT_DESC =
            "원천 판 기준일 (YYYY-MM-DD, 예: 2025-12-31). 원천 파일의 과거 판을 함께 보관하므로, "
            + "이 값을 주면 그 기준일 판의 데이터만 조회합니다. 생략하면 가장 최근 판을 조회합니다. "
            + "응답 항목 srcBaseDt 로 각 데이터의 판 기준일을 확인할 수 있습니다. "
            + "형식이 틀리거나 없는 날짜면 400, 해당 기준일 판이 없으면 빈 결과를 돌려줍니다.";

    /** baseDt 형식: 정확히 YYYY-MM-DD (부호·5자리 연도 등 ISO 확장 표기는 받지 않는다) */
    private static final String BASE_DT_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    /**
     * baseDt 문자열을 날짜로 바꾼다.
     * <p>반드시 각 API 의 try 블록 <b>바깥</b>에서 호출한다. 이 컨트롤러의 try/catch 는 모든 예외를
     * 500(INTERNAL_SERVER_ERROR) 으로 바꿔 던지므로, 안에서 호출하면 형식 오류가 400 이 아닌 500 으로 나간다.</p>
     *
     * @param baseDt 요청 파라미터 원문 (없거나 공백이면 최신 판을 뜻하는 null 반환)
     * @return 판 기준일, 또는 null(최신 판)
     * @throws BusinessException INVALID_FORMAT(400) — 형식이 YYYY-MM-DD 가 아니거나 달력에 없는 날짜(예: 2025-02-30)
     */
    static LocalDate parseBaseDt(String baseDt) {
        if (baseDt == null || baseDt.isBlank()) {
            return null;
        }
        String v = baseDt.trim();
        if (!v.matches(BASE_DT_PATTERN)) {
            throw new BusinessException(ErrorCode.INVALID_FORMAT, "baseDt 는 YYYY-MM-DD 형식이어야 합니다 (예: 2025-12-31)");
        }
        try {
            // ISO_LOCAL_DATE 는 STRICT 해석이라 2025-02-30 같은 없는 날짜를 거부한다
            return LocalDate.parse(v, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ErrorCode.INVALID_FORMAT, "baseDt 가 실제로 있는 날짜가 아닙니다: " + v);
        }
    }



    //#####################################
    //## 01. 장애인 구직자 현황
    //#####################################
    @GetMapping("/job/seeker")
    @Operation(
            summary = "장애인 구직자 검색 조회 (paging)",
            description = "한국장애인고용공단 장애인 구직자 검색 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<PageRes<EmpDisJobseekerDto>>> searchJobSeeker(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject EmpSearchReqJobSeeker searchKeys,
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {

        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            PageRes<EmpDisJobseekerDto> searchRet = null;

            //검색 키워드 확인
            if (searchKeys == null) {
                searchRet = readService.getAllJobSeekers(page, edition);
            } else {
                searchRet = readService.getJobSeekersBySearchKeys(searchKeys, page, edition);
            }

            log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    //#####################################
    //## 05. 장애인 구인 정보
    //#####################################
    @GetMapping("/job/posting")
    @Operation(
            summary = "장애인 구인 정보 검색 조회 (paging)",
            description = "한국장애인고용공단 장애인 구인 정보 검색 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<PageRes<EmpDisJobPostingDto>>> searchJobPosting(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject EmpSearchReqJobPost searchKeys,
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {

        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            PageRes<EmpDisJobPostingDto> searchRet = null;

            //검색 키워드 확인
            if (searchKeys == null) {
                searchRet = readService.getAllJobPosting(page, edition);
            } else {
                searchRet = readService.getJobPostBySearchKeys(searchKeys, page, edition);
            }

            log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    //#####################################
    //## 02. 발달장애인훈련센터 이용자현황
    //#####################################
    @GetMapping("/center/usage")
    @Operation(
            summary = "발달장애인훈련센터 이용자현황 조회",
            description = "한국장애인고용공단 발달장애인훈련센터 이용자현황 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisCenterUsageDto>>> getCenterUsage(
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {
        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            List<EmpDisCenterUsageDto> dtoList = readService.getCenterUsage(edition);
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 03. 장애인 고용의무 현황 통계
    //#####################################
    @GetMapping("/obligation/status")
    @Operation(
            summary = "장애인 고용의무 현황 통계 조회",
            description = "한국장애인고용공단 장애인 고용의무 현황 통계 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisObligationStatusDto>>> getObligationStatus(
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {
        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            List<EmpDisObligationStatusDto> dtoList = readService.getObligationStatuses(edition);
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    //#####################################
    //## 12. 장애인 의무고용 사업체 장애유형별 고용현황
    //#####################################
    @GetMapping("/obligation/dis-type")
    @Operation(
            summary = "장애인 의무고용 사업체 장애유형별 고용현황 조회",
            description = "한국장애인고용공단 장애인 의무고용 사업체 장애유형별 고용현황"
    )
    public ResponseEntity<ApiResDto<List<EmpDisObligationByTypeDto>>> getObligationByType( HttpServletRequest request ) {
        try {
            List<EmpDisObligationByTypeDto> dtoList = readService.getObligationByType();
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 14. 산업별 의무고용 현황
    //#####################################
    @GetMapping("/obligation/industry")
    @Operation(
            summary = "산업별 장애인 의무고용 현황 조회",
            description = "한국장애인고용공단 산업별 장애인 의무고용 현황 통계 (연도별 데이터, 최신 연도부터 정렬)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisObligationByIndustDto>>> getObligationByIndustry( HttpServletRequest request ) {
        try {
            List<EmpDisObligationByIndustDto> dtoList = readService.getObligationByIndustry();
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 15. 장애인 의무고용 현황
    //#####################################
    @GetMapping("/obligation/fulfillment")
    @Operation(
            summary = "장애인 의무고용 - 사업체 현황 조회",
            description = "고용노동부 장애인 의무고용 - 사업체 현황 통계 (연도별 데이터, 최신 연도부터 정렬)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisObligationFulfillmentDto>>> getObligationFulfillment( HttpServletRequest request ) {
        try {
            List<EmpDisObligationFulfillmentDto> dtoList = readService.getObligationFulfillment();
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 04. 장애인 고용컨설팅
    //#####################################
    @GetMapping("/emp/consul-his")
    @Operation(
            summary = "장애인 고용컨설팅 이력 조회",
            description = "한국장애인고용공단 장애인 고용컨설팅 이력 정보 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisConsultingHisDto>>> getConsultingHis(
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {
        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            List<EmpDisConsultingHisDto> dtoList = readService.getConsultingHis(edition);
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 7. 지역별 장애인 고용 현황
    //#####################################
    @GetMapping("/emp/region")
    @Operation(
            summary = "지역별 장애인 고용 현황 조회",
            description = "한국장애인고용공단 지역별 장애인 고용 현황 (연도별 데이터, 최신 연도부터 정렬)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisRegionalStatusDto>>> getEmpStatusByRegion( HttpServletRequest request ) {
        try {
            List<EmpDisRegionalStatusDto> dtoList = readService.getEmpStatusByRegion();
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 13. 신규고용장려금 지역별 지급 현황
    //#####################################
    @GetMapping("/emp/incentive")
    @Operation(
            summary = "장애인 신규고용장려금 지역별 지급 현황 조회",
            description = "한국장애인고용공단 신규고용장려금 지역별 지급 현황 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisEmpIncentiveDto>>> getEmpIncentive(
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {
        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            List<EmpDisEmpIncentiveDto> dtoList = readService.getEmpIncentive(edition);
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    //#####################################
    //## 06. 장애인고용 부담금감면 연계고용사업장 정보
    //#####################################
    @GetMapping("/workplace/burden-redct")
    @Operation(
            summary = "장애인고용 부담금감면 연계고용사업장 정보 조회",
            description = "한국장애인고용공단 장애인고용 부담금감면 연계고용사업장 정보 (연도별 데이터, 최신 연도부터 정렬)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisBurdenWorkplaceDto>>> getBurdenRedctWorkplace (HttpServletRequest request){
        try {
            List<EmpDisBurdenWorkplaceDto> dtoList = readService.getBurdenRedctWorkplace();
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 11. 장애인 표준사업장 현황
    //#####################################
    @GetMapping("/workplace/standard")
    @Operation(
            summary = "장애인 표준사업장 현황 조회!",
            description = "한국장애인고용공단 장애인 표준사업장 현황 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisStdWorkplaceDto>>> getStandardWorkplace(
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {
        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            List<EmpDisStdWorkplaceDto> dtoList = readService.getStandardWorkplace(edition);
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    //#####################################
    //## 08. 고용개발원 교육정보(장애인고용 전문인력 교육과정)
    //#####################################
    @GetMapping("/edu/training/staff")
    @Operation(
            summary = "고용개발원 교육정보 조회",
            description = "한국장애인고용공단 고용개발원 교육정보(장애인고용 전문인력 교육과정) (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisStaffTrainCrsDto>>> getStaffTrainingCourse(
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {
        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            List<EmpDisStaffTrainCrsDto> dtoList = readService.getStaffTrainCrs(edition);
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 10. 장애인기업종합지원센터 창업넷 일반강좌 정보
    //#####################################
    @GetMapping("/edu/lecture/startup")
    @Operation(
            summary = "창업넷 일반강좌 정보 조회",
            description = "장애인기업종합지원센터 창업넷 일반강좌 정보 (연도별 데이터, 최신 연도부터 정렬)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisStartupLectureDto>>> getStartupLecture (HttpServletRequest request){
        try {
            List<EmpDisStartupLectureDto> dtoList = readService.getStartupLecture();
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    //#####################################
    //## 09. 한국장애인개발원 발달장애인 지원 기관 및 제공서비스
    //#####################################
    @GetMapping("/dev/support-org")
    @Operation(
            summary = "발달장애인 지원 기관 및 제공서비스 조회",
            description = "한국장애인개발원 발달장애인 지원 기관 및 제공서비스 (원천 파일의 판별 보관: 기본은 최신 판, baseDt 로 과거 판 선택)"
    )
    public ResponseEntity<ApiResDto<List<EmpDisDevSupportOrgDto>>> getDevDisSupportOrg(
            @Parameter(description = BASE_DT_DESC, example = "2025-12-31")
            @RequestParam(value = "baseDt", required = false) String baseDt,
            HttpServletRequest request) {
        // 판 기준일 검증은 try 밖에서: 형식 오류를 400 그대로 내보내기 위함 (parseBaseDt 주석 참고)
        LocalDate edition = parseBaseDt(baseDt);

        try {
            List<EmpDisDevSupportOrgDto> dtoList = readService.getDevDisSupportOrg(edition);
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }






}
