package com.sweetk.iitp.api.controller.v1.emp;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.emp.EmpDisJobPostingDto;
import com.sweetk.iitp.api.dto.emp.EmpDisJobseekerDto;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
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
            summary = "장애인 구직자 검색 조회",
            description = "장애인 구직자 검색 조회 (paging):"
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
            summary = "장애인 구인 정보 검색 조회",
            description = "장애인 구인 정보 검색 조회 (paging):"
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
}
