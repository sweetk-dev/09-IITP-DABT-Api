package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorLocation;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.PoiSubwayElevatorReadService;
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

@Tag(name = "이동형 - 편의시설 - 지하철 엘리베이터 POI API V1", description = "이동형 - 편의시설 - 지하철 엘리베이터 POI 관련 API")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_ELEVATOR + "/subway")
@RequiredArgsConstructor
public class PoiSubwayElevatorController {

    private final PoiSubwayElevatorReadService subwayElevatorReadService;



    /*******************************
     ** 지하철 엘리베이터 ID로 조회
     *******************************/
    @GetMapping("/{subwayId}")
    @Operation(
        summary = "지하철 엘리베이터 상세 조회",
        description = "지하철 엘리베이터 ID로 상세 정보 조회"
    )
    public ResponseEntity<ApiResDto<PoiSubwayElevator>> getSubwayElevatorById(
            @Parameter(description = "지하철 엘리베이터 ID", required = true)
            @PathVariable Integer subwayId,
            HttpServletRequest request) {

        return subwayElevatorReadService.findById(subwayId)
                .map(elevator -> ResponseEntity.ok(ApiResDto.success(elevator)))
                .orElse(ResponseEntity.notFound().build());
    }


    /*******************************
     * 전체 지하철 엘리베이터 조회
     *******************************/
    @GetMapping("/all")
    @Operation(
        summary = "전체 지하철 엘리베이터 조회 (전체 결과)",
        description = "모든 지하철 엘리베이터 목록을 조회합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiSubwayElevator>>> getAllSubwayElevators(
            HttpServletRequest request) {
        try {
            List<PoiSubwayElevator> result = subwayElevatorReadService.getAllSubwayElevators();
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/paging")
    @Operation(
        summary = "전체 지하철 엘리베이터 조회 (페이징)",
        description = "모든 지하철 엘리베이터 목록을 조회합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> getAllSubwayElevatorsPaging(
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        try {
            PageRes<PoiSubwayElevator> result = subwayElevatorReadService.getAllSubwayElevators(page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     ** 시도별 지하철 엘리베이터 조회
     *******************************/
    @GetMapping("/sido/{sidoCode}")
    @Operation(
        summary = "시도별 지하철 엘리베이터 조회 (전체 결과)",
        description = "시도 코드로 지하철 엘리베이터 목록을 조회합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiSubwayElevator>>> getSubwayElevatorsBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "91100000")
            @PathVariable String sidoCode,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}", request.getRequestURI(), sidoCode);
        
        try {
            List<PoiSubwayElevator> result = subwayElevatorReadService.getSubwayElevatorsBySido(sidoCode);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido/{sidoCode}/paging")
    @Operation(
        summary = "시도별 지하철 엘리베이터 조회 (페이징)",
        description = "시도 코드로 지하철 엘리베이터 목록을 조회합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> getSubwayElevatorsBySidoPaging(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "91100000")
            @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}, 페이지: {}", request.getRequestURI(), sidoCode, page);
        
        try {
            PageRes<PoiSubwayElevator> result = subwayElevatorReadService.getSubwayElevatorsBySidoPaging(sidoCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     ** 시군구별 지하철 엘리베이터 조회
     *******************************/
    @GetMapping("/sido/{sidoCode}/{sigunguCode}")
    @Operation(
        summary = "시군구별 지하철 엘리베이터 조회 (전체 결과)",
        description = "시도 코드와 시군구 코드로 지하철 엘리베이터 목록을 조회합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiSubwayElevator>>> getSubwayElevatorsBySigungu(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "91100000")
            @PathVariable String sidoCode,
            @Parameter(description = "시군구 코드 (11자리)", required = true, example = "1111000000")
            @PathVariable String sigunguCode,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}, 시군구 코드: {}", request.getRequestURI(), sidoCode, sigunguCode);
        
        try {
            List<PoiSubwayElevator> result = subwayElevatorReadService.getSubwayElevatorsBySigungu(sidoCode, sigunguCode);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido/{sidoCode}/{sigunguCode}/paging")
    @Operation(
        summary = "시군구별 지하철 엘리베이터 조회 (페이징)",
        description = "시도 코드와 시군구 코드로 지하철 엘리베이터 목록을 조회합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> getSubwayElevatorsBySigunguPaging(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "91100000")
            @PathVariable String sidoCode,
            @Parameter(description = "시군구 코드 (11자리)", required = true, example = "1111000000")
            @PathVariable String sigunguCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}, 시군구 코드: {}, 페이지: {}", request.getRequestURI(), sidoCode, sigunguCode, page);
        
        try {
            PageRes<PoiSubwayElevator> result = subwayElevatorReadService.getSubwayElevatorsBySigunguPaging(sidoCode, sigunguCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    /*******************************
     ** 카테고리 기반 지하철 엘리베이터 조회
     *******************************/
    @GetMapping("/search")
    @Operation(
        summary = "지하철 엘리베이터 카테고리 검색 (전체 결과)",
        description = "지하철역명, 시도코드, 노드유형으로 지하철 엘리베이터를 검색합니다. 검색 조건이 있어야 합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiSubwayElevator>>> searchByCategory(
            @Valid @ParameterObject PoiSubwayElevatorSearchCatReq searchKeys,
            HttpServletRequest request) {
        
        try {
            if (searchKeys == null) {
                log.error("[{}] : no input parameter", request.getRequestURI());
                throw new BusinessException(ErrorCode.INVALID_PARAMETER);
            }

            List<PoiSubwayElevator> searchRet = subwayElevatorReadService.getSubwayElevatorsByCategory(searchKeys);
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/paging")
    @Operation(
        summary = "지하철 엘리베이터 카테고리 검색 (페이징)",
        description = "지하철역명, 시도코드, 노드유형으로 지하철 엘리베이터를 검색합니다. 검색 조건이 있어야 합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> searchByCategoryPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiSubwayElevatorSearchCatReq searchKeys,
            HttpServletRequest request) {
        
        try {

            if (searchKeys == null) {
                log.error("[{}] : no input parameter", request.getRequestURI());
                throw new BusinessException(ErrorCode.INVALID_PARAMETER);
            }

            PageRes<PoiSubwayElevator> searchRet = subwayElevatorReadService.getSubwayElevatorsByCategoryPaging(searchKeys, page);
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /*******************************
     ** 위치 기반 기반 지하철 엘리베이터 조회
     *******************************/
    @GetMapping("/search/location")
    @Operation(
        summary = "지하철 엘리베이터 위치 기반 검색 (전체 결과)",
        description = "위치 기준 반경 내 지하철 엘리베이터를 검색합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiSubwayElevatorLocation>>> searchByLocation(
            @Valid @ParameterObject PoiSubwayElevatorSearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        List<PoiSubwayElevatorLocation> searchRet = subwayElevatorReadService.getSubwayElevatorByLocation(searchKeys);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }

    @GetMapping("/search/location/paging")
    @Operation(
        summary = "지하철 엘리베이터 위치 기반 검색 (페이징)",
        description = "위치 기준 반경 내 지하철 엘리베이터를 검색합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevatorLocation>>> searchByLocationPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiSubwayElevatorSearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        PageRes<PoiSubwayElevatorLocation> searchRet = subwayElevatorReadService.getSubwayElevatorsByLocationPaging(searchKeys, page);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }
} 