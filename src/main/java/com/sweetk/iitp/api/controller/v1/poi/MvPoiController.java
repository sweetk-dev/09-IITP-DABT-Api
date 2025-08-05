package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.constant.poi.MvPoiCategoryType;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchCatReq;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.MvPoiReadService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI)
@RequiredArgsConstructor
@Tag(name = "이동형 POI API V1", description = "이동형 API ")
public class MvPoiController {

    private final MvPoiReadService mvPoiReadService;

    @GetMapping("/{poiId}")
    @Operation(
        summary = "이동형 POI 상세 조회",
        description = "이동형 POI ID로 상세 정보 조회"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = MvPoi.class))),
        @ApiResponse(responseCode = "404", description = "POI를 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResDto<MvPoi>> getPoiById(
            @Parameter(description = "POI ID", required = true)
            @PathVariable Long poiId,
            HttpServletRequest request) {
        
        return mvPoiReadService.findById(poiId)
                .map(poi -> ResponseEntity.ok(ApiResDto.success(poi)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryType}")
    @Operation(
        summary = "이동형 POI 카테고리별 조회",
        description = "카테고리 유형별로 이동형 POI를 조회"
    )
    public ResponseEntity<ApiResDto<PageRes<MvPoi>>> getPoiByCategory(
              @Parameter(description = "카테고리 유형", required = true,
             example = "tourist_spot",
             schema = @Schema(allowableValues = {"tourist_spot", "restaurant", "shopping", "accommodation"}))
              @PathVariable MvPoiCategoryType categoryType,
              @Valid @ParameterObject PageReq page,
              HttpServletRequest request) {
        try {
            PageRes<MvPoi> result = mvPoiReadService.getPoiByCategoryType(categoryType.getCode(), page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @Operation(
            summary = "이동형 POI 카테고리 검색 조회",
            description = "이동형 POI 카테고리 검색 조회 (paging):"
    )
    public ResponseEntity<ApiResDto<PageRes<MvPoi>>> searchByCategory(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject MvPoiSearchCatReq searchKeys,
            HttpServletRequest request) {

        PageRes<MvPoi> searchRet = null;

        try {
            //검색 키워드 확인
            if (searchKeys == null) {
                searchRet = mvPoiReadService.getAllPoi(page);
            } else {
                searchRet = mvPoiReadService.getPoiByCategory(searchKeys, page);
            }

            log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }


    }


    @GetMapping("/search/location")
    @Operation(
            summary = "이동형 POI 위치기반 검색 조회",
            description = "이동형 POI 위치기반 검색 조회 (paging):"
    )
    public ResponseEntity<ApiResDto<PageRes<MvPoi>>> searchByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject MvPoiSearchLocReq searchKeys,
            HttpServletRequest request ) {

        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString() );

        PageRes<MvPoi> searchRet = mvPoiReadService.getPoiByLocation( searchKeys, page );

        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }
} 