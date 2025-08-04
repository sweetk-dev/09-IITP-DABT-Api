package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorSearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.PoiSubwayElevatorReadService;
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

@Tag(name = "지하철 엘리베이터 POI", description = "지하철 엘리베이터 POI 관련 API")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI + "/subway-elevator")
@RequiredArgsConstructor
public class PoiSubwayElevatorController {

    private final PoiSubwayElevatorReadService poiSubwayElevatorReadService;

    @GetMapping("/{subwayId}")
    @Operation(
        summary = "지하철 엘리베이터 상세 조회",
        description = "지하철 엘리베이터 ID로 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = PoiSubwayElevator.class))),
        @ApiResponse(responseCode = "404", description = "지하철 엘리베이터를 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PoiSubwayElevator>> getSubwayElevatorById(
            @Parameter(description = "지하철 엘리베이터 ID", required = true)
            @PathVariable Integer subwayId,
            HttpServletRequest request) {
        
        log.debug("[{}] : 지하철 엘리베이터 ID: {}", request.getRequestURI(), subwayId);
        
        return poiSubwayElevatorReadService.findById(subwayId)
                .map(elevator -> ResponseEntity.ok(ApiResDto.success(elevator)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sido/{sidoCode}")
    @Operation(
        summary = "시도별 지하철 엘리베이터 조회",
        description = "시도 코드로 지하철 엘리베이터 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = PageRes.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 시도 코드"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> getSubwayElevatorsBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "1100000")
            @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}, 페이지: {}", request.getRequestURI(), sidoCode, page);
        
        try {
            PageRes<PoiSubwayElevator> result = poiSubwayElevatorReadService.getSubwayElevatorsBySido(sidoCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido/{sidoCode}/{sigunguCode}")
    @Operation(
        summary = "시군구별 지하철 엘리베이터 조회",
        description = "시도 코드와 시군구 코드로 지하철 엘리베이터 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = PageRes.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 시도/시군구 코드"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> getSubwayElevatorsBySigungu(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "1100000")
            @PathVariable String sidoCode,
            @Parameter(description = "시군구 코드 (5자리)", required = true, example = "11110")
            @PathVariable String sigunguCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        
        log.debug("[{}] : 시도 코드: {}, 시군구 코드: {}, 페이지: {}", request.getRequestURI(), sidoCode, sigunguCode, page);
        
        try {
            PageRes<PoiSubwayElevator> result = poiSubwayElevatorReadService.getSubwayElevatorsBySigungu(sidoCode, sigunguCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @Operation(
        summary = "지하철 엘리베이터 카테고리 검색",
        description = "지하철역명, 시도코드, 역코드, 노드유형으로 지하철 엘리베이터를 검색합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
            content = @Content(schema = @Schema(implementation = PageRes.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> searchByCategory(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiSubwayElevatorSearchCatReq searchKeys,
            HttpServletRequest request) {
        
        PageRes<PoiSubwayElevator> searchRet = null;
        
        try {
            // 검색 키워드 확인
            if (searchKeys == null) {
                searchRet = poiSubwayElevatorReadService.getSubwayElevatorsByCategory(null, page);
            } else {
                searchRet = poiSubwayElevatorReadService.getSubwayElevatorsByCategory(searchKeys, page);
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
        summary = "지하철 엘리베이터 위치 기반 검색",
        description = "위치 기준 반경 내 지하철 엘리베이터를 검색합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공",
            content = @Content(schema = @Schema(implementation = PageRes.class))),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<PageRes<PoiSubwayElevator>>> searchByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiSubwayElevatorSearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        PageRes<PoiSubwayElevator> searchRet = poiSubwayElevatorReadService.getSubwayElevatorsByLocation(searchKeys, page);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }
} 