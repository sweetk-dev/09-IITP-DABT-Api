package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilitySearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.PoiTourBfFacilityReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "무장애 관광지 시설", description = "무장애(Barrier Free) 관광지 시설 관련 API")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI + "/tour-bf-facility")
@RequiredArgsConstructor
public class PoiTourBfFacilityController {

    private final PoiTourBfFacilityReadService poiTourBfFacilityReadService;

    @GetMapping("/{fcltId}")
    @Operation(
        summary = "무장애 관광지 시설 상세 조회",
        description = "무장애 관광지 시설 ID로 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = PoiTourBfFacility.class))),
        @ApiResponse(responseCode = "404", description = "무장애 관광지 시설을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PoiTourBfFacility>> getTourBfFacilityById(
            @Parameter(description = "무장애 관광지 시설 ID", required = true)
            @PathVariable Integer fcltId,
            HttpServletRequest request) {
        
        log.debug("[{}] : 무장애 관광지 시설 ID: {}", request.getRequestURI(), fcltId);
        
        return poiTourBfFacilityReadService.findById(fcltId)
                .map(facility -> ResponseEntity.ok(ApiResDto.success(facility)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sido/{sidoCode}")
    @Operation(
        summary = "시도별 무장애 관광지 시설 조회",
        description = "시도 코드로 무장애 관광지 시설 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = PageRes.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 시도 코드"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PageRes<PoiTourBfFacility>>> getTourBfFacilitiesBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "1100000")
            @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}, 페이지: {}", request.getRequestURI(), sidoCode, page);
        
        try {
            PageRes<PoiTourBfFacility> result = poiTourBfFacilityReadService.getTourBfFacilitiesBySido(sidoCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @Operation(
        summary = "무장애 관광지 시설 카테고리 검색",
        description = "시설명, 시도코드, 장애인시설로 무장애 관광지 시설을 검색합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
            content = @Content(schema = @Schema(implementation = PageRes.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PageRes<PoiTourBfFacility>>> searchByCategory(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiTourBfFacilitySearchCatReq searchKeys,
            HttpServletRequest request) {
        
        PageRes<PoiTourBfFacility> searchRet = null;
        
        try {
            // 검색 키워드 확인
            if (searchKeys == null) {
                searchRet = poiTourBfFacilityReadService.getTourBfFacilitiesByCategory(null, page);
            } else {
                searchRet = poiTourBfFacilityReadService.getTourBfFacilitiesByCategory(searchKeys, page);
            }
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/location")
    @Operation(
        summary = "무장애 관광지 시설 위치 기반 검색",
        description = "위치 기준 반경 내 무장애 관광지 시설을 검색합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
            content = @Content(schema = @Schema(implementation = PageRes.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PageRes<PoiTourBfFacility>>> searchByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiTourBfFacilitySearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        PageRes<PoiTourBfFacility> searchRet = poiTourBfFacilityReadService.getTourBfFacilitiesByLocation(searchKeys, page);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }
} 