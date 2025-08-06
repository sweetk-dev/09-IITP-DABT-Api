package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfoSearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.PoiPublicToiletInfoReadService;
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

@Tag(name = "편의시설 - 공중 화장실 정보", description = "편의시설 - 공중 화장실 위치 및 시설 정보 관련 API")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_PUBLIC_PUBLIC_TOILET)
@RequiredArgsConstructor
public class PoiPublicToiletInfoController {

    private final PoiPublicToiletInfoReadService poiPublicToiletInfoReadService;

    @GetMapping("/{toiletId}")
    @Operation(
            summary = "공중 화장실 상세 조회",
            description = "공중 화장실 ID로 상세 정보 조회."
    )
    public ResponseEntity<ApiResDto<PoiPublicToiletInfo>> getPublicToiletById(
            @Parameter(description = "공중 화장실 ID", required = true)
            @PathVariable Integer toiletId) {

        log.info("공중 화장실 상세 조회 요청 - ID: {}", toiletId);

        return poiPublicToiletInfoReadService.findById(toiletId)
                .map(toilet -> ResponseEntity.ok(ApiResDto.success(toilet)))
                .orElseThrow(()->new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }

    @GetMapping("/all")
    @Operation(
            summary = "전체 공중 화장실 조회 (전체 결과)",
            description = "모든 공중 화장실 목록을 조회합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToiletInfo>>> getAllPublicToilets(
            HttpServletRequest request) {
        try {
            List<PoiPublicToiletInfo> result = poiPublicToiletInfoReadService.getAllPublicToilets();
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/paging")
    @Operation(
            summary = "전체 공중 화장실 조회 (페이징)",
            description = "모든 공중 화장실 목록을 조회합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletInfo>>> getAllPublicToiletsPaging(
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        try {
            PageRes<PoiPublicToiletInfo> result = poiPublicToiletInfoReadService.getAllPublicToilets(page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido/{sidoCode}")
    @Operation(
            summary = "시도별 공중 화장실 조회 (전체 결과)",
            description = "시도 코드로 공중 화장실 목록을 조회합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToiletInfo>>> getPublicToiletsBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "9110000")
            @PathVariable String sidoCode,
            HttpServletRequest request) {
        try {
            List<PoiPublicToiletInfo> result = poiPublicToiletInfoReadService.getPublicToiletsBySido(sidoCode);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sido/{sidoCode}/paging")
    @Operation(
            summary = "시도별 공중 화장실 조회 (페이징)",
            description = "시도 코드로 공중 화장실 목록을 조회합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletInfo>>> getPublicToiletsBySidoPaging(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "9110000")
            @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        try {
            PageRes<PoiPublicToiletInfo> result = poiPublicToiletInfoReadService.getPublicToiletsBySidoPaging(sidoCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @Operation(
            summary = "공중 화장실 카테고리 검색 (전체 결과)",
            description = "이름, 시도코드, 유형, 24시간개방으로 공중 화장실을 검색합니다. 검색 조건이 있어야 합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToiletInfo>>> searchByCategory(
            @Valid @ParameterObject PoiPublicToiletInfoSearchCatReq searchKeys,
            HttpServletRequest request) {

        try {
            List<PoiPublicToiletInfo> searchRet = poiPublicToiletInfoReadService.getPublicToiletsByCategory(searchKeys);
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/paging")
    @Operation(
            summary = "공중 화장실 카테고리 검색 (페이징)",
            description = "이름, 시도코드, 유형, 24시간개방으로 공중 화장실을 검색합니다. 검색 조건이 있어야 합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletInfo>>> searchByCategoryPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiPublicToiletInfoSearchCatReq searchKeys,
            HttpServletRequest request) {

        try {
            PageRes<PoiPublicToiletInfo> searchRet = poiPublicToiletInfoReadService.getPublicToiletsByCategoryPaging(searchKeys, page);

            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/location")
    @Operation(
        summary = "공중 화장실 위치 기반 검색 (전체 결과)",
        description = "위치 기준 반경 내 공중 화장실을 검색합니다. 전체 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<List<PoiPublicToiletInfo>>> searchByLocation(
            @Valid @ParameterObject PoiPublicToiletInfoSearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        List<PoiPublicToiletInfo> searchRet = poiPublicToiletInfoReadService.getPublicToiletsByLocation(searchKeys);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }

    @GetMapping("/search/location/paging")
    @Operation(
            summary = "공중 화장실 위치 기반 검색 (페이징)",
            description = "위치 기준 반경 내 공중 화장실을 검색합니다. 페이징 처리된 결과를 반환합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletInfo>>> searchByLocationPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiPublicToiletInfoSearchLocReq searchKeys,
            HttpServletRequest request) {

        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());

        PageRes<PoiPublicToiletInfo> searchRet = poiPublicToiletInfoReadService.getPublicToiletsByLocationPaging(searchKeys, page);

        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }
} 