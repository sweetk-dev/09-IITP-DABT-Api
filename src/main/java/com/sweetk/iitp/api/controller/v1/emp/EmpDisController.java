package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.emp.*;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.emp.EmpDisReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    //#####################################
    //## 01. 장애인 구직자 현황
    //#####################################
    @GetMapping("/job/seeker")
    @Operation(
            summary = "장애인 구직자 검색 조회 (paging)",
            description = "한국장애인고용공단 장애인 구직자 검색 "
    )
    public ResponseEntity<ApiResDto<PageRes<EmpDisJobseekerDto>>> searchJobSeeker(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject EmpSearchReqJobSeeker searchKeys,
            HttpServletRequest request) {

        try {
            PageRes<EmpDisJobseekerDto> searchRet = null;

            //검색 키워드 확인
            if (searchKeys == null) {
                searchRet = readService.getAllJobSeekers(page);
            } else {
                searchRet = readService.getJobSeekersBySearchKeys(searchKeys, page);
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
            description = "한국장애인고용공단 장애인 구인 정보 검색"
    )
    public ResponseEntity<ApiResDto<PageRes<EmpDisJobPostingDto>>> searchJobPosting(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject EmpSearchReqJobPost searchKeys,
            HttpServletRequest request) {

        try {
            PageRes<EmpDisJobPostingDto> searchRet = null;

            //검색 키워드 확인
            if (searchKeys == null) {
                searchRet = readService.getAllJobPosting(page);
            } else {
                searchRet = readService.getJobPostBySearchKeys(searchKeys, page);
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
            description = "한국장애인고용공단 발달장애인훈련센터 이용자현황"
    )
    public ResponseEntity<ApiResDto> getCenterUsage( HttpServletRequest request ) {
        try {
            List<EmpDisCenterUsageDto> dtoList = readService.getCenterUsage();
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
            description = "한국장애인고용공단 장애인 고용의무 현황 통계 "
    )
    public ResponseEntity<ApiResDto> getObligationStatus( HttpServletRequest request ) {
        try {
            List<EmpDisObligationStatusDto> dtoList = readService.getObligationStatuses();
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
    public ResponseEntity<ApiResDto> getObligationByType( HttpServletRequest request ) {
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
            description = "한국장애인고용공단 산업별 장애인 의무고용 현황 통계 "
    )
    public ResponseEntity<ApiResDto> getObligationByIndustry( HttpServletRequest request ) {
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
            description = "고용노동부 장애인 의무고용 - 사업체 현황 통계 "
    )
    public ResponseEntity<ApiResDto> getObligationFulfillment( HttpServletRequest request ) {
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
            description = "한국장애인고용공단 장애인 고용컨설팅 이력 정보 "
    )
    public ResponseEntity<ApiResDto> getConsultingHis( HttpServletRequest request ) {
        try {
            List<EmpDisConsultingHisDto> dtoList = readService.getConsultingHis();
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
            description = "한국장애인고용공단 지역별 장애인 고용 현황 "
    )
    public ResponseEntity<ApiResDto> getEmpStatusByRegion( HttpServletRequest request ) {
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
            description = "한국장애인고용공단 신규고용장려금 지역별 지급 현황 "
    )
    public ResponseEntity<ApiResDto> getEmpIncentive( HttpServletRequest request ) {
        try {
            List<EmpDisEmpIncentiveDto> dtoList = readService.getEmpIncentive();
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
            description = "한국장애인고용공단 장애인고용 부담금감면 연계고용사업장 정보"
    )
    public ResponseEntity<ApiResDto> getBurdenRedctWorkplace (HttpServletRequest request){
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
            summary = "장애인 표준사업장 현황 조회",
            description = "한국장애인고용공단 장애인 표준사업장 현황"
    )
    public ResponseEntity<ApiResDto> getStandardWorkplace (HttpServletRequest request){
        try {
            List<EmpDisStdWorkplaceDto> dtoList = readService.getStandardWorkplace();
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
            description = "한국장애인고용공단 고용개발원 교육정보(장애인고용 전문인력 교육과정)"
    )
    public ResponseEntity<ApiResDto> getStaffTrainingCourse (HttpServletRequest request){
        try {
            List<EmpDisStaffTrainCrsDto> dtoList = readService.getStaffTrainCrs();
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
            description = "장애인기업종합지원센터 창업넷 일반강좌 정보"
    )
    public ResponseEntity<ApiResDto> getStartupLecture (HttpServletRequest request){
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
            description = "한국장애인개발원 발달장애인 지원 기관 및 제공서비스"
    )
    public ResponseEntity<ApiResDto> getDevDisSupportOrg (HttpServletRequest request){
        try {
            List<EmpDisDevSupportOrgDto> dtoList = readService.getDevDisSupportOrg();
            return ResponseEntity.ok(ApiResDto.success(dtoList));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }






}
