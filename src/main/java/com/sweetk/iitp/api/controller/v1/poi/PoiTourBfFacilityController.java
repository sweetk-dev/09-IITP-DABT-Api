package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilityLocation;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.PoiTourBfFacilityReadService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "이동형 - 편의시설 - 무장애 관광지 시설 POI API V1", description = "이동형 - 편의시설 -무장애(Barrier Free) 관광지 시설 관련 API")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_BF_TOUR)
@RequiredArgsConstructor
public class PoiTourBfFacilityController {

    private final PoiTourBfFacilityReadService tourBfFacilityReadService;


    /*******************************
     ** 무장애 관광지 시설 ID로 조회
     *******************************/
    @GetMapping("/{fcltId}")
    @Operation(
        summary = "무장애 관광지 시설 상세 조회",
        description = "무장애 관광지 시설 ID로 상세 정보를 조회합니다."
    )
    public ResponseEntity<ApiResDto<PoiTourBfFacility>> getTourBfFacilityById(
            @Parameter(description = "무장애 관광지 시설 ID", required = true)
            @PathVariable Integer fcltId,
            HttpServletRequest request) {
        
        log.debug("[{}] : 무장애 관광지 시설 ID: {}", request.getRequestURI(), fcltId);
        
        return tourBfFacilityReadService.findById(fcltId)
                .map(facility -> ResponseEntity.ok(ApiResDto.success(facility)))
                .orElse(ResponseEntity.notFound().build());
    }


    /*******************************
     ** 전체 무장애 관광지 시설 조회
     *******************************/
    @GetMapping("/all")
    @Operation(
            summary = "전체 무장애 관광지 시설 조회 (전체 결과)",
            description = "모든 무장애 관광지 시설을 조회합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiTourBfFacility>>> getAllTourBfFacilities(HttpServletRequest request) {
        log.debug("[{}] : 전체 무장애 관광지 시설 조회", request.getRequestURI());

        List<PoiTourBfFacility> result = tourBfFacilityReadService.getAllTourBfFacilities();
        return ResponseEntity.ok(ApiResDto.success(result));
    }

    @GetMapping("/all/paging")
    @Operation(
            summary = "전체 무장애 관광지 시설 조회 (페이징)",
            description = "모든 무장애 관광지 시설을 페이징하여 조회합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiTourBfFacility>>> getAllTourBfFacilitiesPaging(
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        log.debug("[{}] : 전체 무장애 관광지 시설 조회 (페이징)", request.getRequestURI());

        PageRes<PoiTourBfFacility> result = tourBfFacilityReadService.getAllTourBfFacilitiesPaging(page);
        return ResponseEntity.ok(ApiResDto.success(result));
    }



    /*******************************
     ** 시도별 무장애 관광지 시설 조회
     *******************************/
    @GetMapping("/sido/{sidoCode}")
    @Operation(
        summary = "시도별 무장애 관광지 시설 조회 (전체 결과)",
        description = "시도 코드로 무장애 관광지 시설 목록을 전체 조회합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiTourBfFacility>>> getTourBfFacilitiesBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "91100000")
            @PathVariable String sidoCode,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}", request.getRequestURI(), sidoCode);
        
        try {
            List<PoiTourBfFacility> result = tourBfFacilityReadService.getTourBfFacilitiesBySido(sidoCode);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido/{sidoCode}/paging")
    @Operation(
        summary = "시도별 무장애 관광지 시설 조회 (페이징)",
        description = "시도 코드로 무장애 관광지 시설 목록을 페이징하여 조회합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiTourBfFacility>>> getTourBfFacilitiesBySidoPaging(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "91100000")
            @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}, 페이지: {}", request.getRequestURI(), sidoCode, page);
        
        try {
            PageRes<PoiTourBfFacility> result = tourBfFacilityReadService.getTourBfFacilitiesBySidoPaging(sidoCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }




    /*******************************
     ** 카테고리 기반 무장애 관광지 시설 조회
     *******************************/
    @GetMapping("/search")
    @Operation(
        summary = "무장애 관광지 시설 카테고리 검색 (전체 결과)",
        description = "시설명, 시도코드, 장애인시설로 무장애 관광지 시설을 검색합니다. 검색 조건이 없으면 빈 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiTourBfFacility>>> searchByCategory(
            @Valid @ParameterObject PoiTourBfFacilitySearchCatReq searchKeys,
            HttpServletRequest request) {
        
        try {
            if (searchKeys == null) {
                log.error("[{}] : no input parameter", request.getRequestURI());
                throw new BusinessException(ErrorCode.INVALID_PARAMETER);
            }

            List<PoiTourBfFacility> searchRet = tourBfFacilityReadService.getTourBfFacilitiesByCategory(searchKeys);
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/paging")
    @Operation(
        summary = "무장애 관광지 시설 카테고리 검색 (페이징)",
        description = "시설명, 시도코드, 장애인시설로 무장애 관광지 시설을 페이징하여 검색합니다. 검색 조건이 없으면 빈 페이지를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiTourBfFacility>>> searchByCategoryPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiTourBfFacilitySearchCatReq searchKeys,
            HttpServletRequest request) {
        
        try {
            if (searchKeys == null) {
                log.error("[{}] : no input parameter", request.getRequestURI());
                throw new BusinessException(ErrorCode.INVALID_PARAMETER);
            }

            PageRes<PoiTourBfFacility> searchRet = tourBfFacilityReadService.getTourBfFacilitiesByCategoryPaging(searchKeys, page);
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }




    /*******************************
     ** 위치 기반 무장애 관광지 시설 조회
     *******************************/

    @GetMapping("/search/location")
    @Operation(
        summary = "무장애 관광지 시설 위치 기반 검색 (전체 결과)",
        description = "위치 기준 반경 내 무장애 관광지 시설을 검색합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiTourBfFacilityLocation>>> searchByLocation(
            @Valid @ParameterObject PoiTourBfFacilitySearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        List<PoiTourBfFacilityLocation> searchRet = tourBfFacilityReadService.getTourBfFacilitiesByLocation(searchKeys);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }

    @GetMapping("/search/location/paging")
    @Operation(
        summary = "무장애 관광지 시설 위치 기반 검색 (페이징)",
        description = "위치 기준 반경 내 무장애 관광지 시설을 페이징하여 검색합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiTourBfFacilityLocation>>> searchByLocationPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiTourBfFacilitySearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        PageRes<PoiTourBfFacilityLocation> searchRet = tourBfFacilityReadService.getTourBfFacilitiesByLocationPaging(searchKeys, page);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }


} 