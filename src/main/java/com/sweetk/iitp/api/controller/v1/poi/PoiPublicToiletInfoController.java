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

@Tag(name = "공중 화장실 정보", description = "공중 화장실 위치 및 시설 정보 관련 API")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI + "/public-toilet")
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
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/sido/{sidoCode}")
    @Operation(
            summary = "시도별 공중 화장실 조회",
            description = "시도 코드로 공중 화장실 목록을 조회합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletInfo>>> getPublicToiletsBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "9110000")
            @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page,
            HttpServletRequest request) {
        try {
            PageRes<PoiPublicToiletInfo> result = poiPublicToiletInfoReadService.getPublicToiletsBySido(sidoCode, page);
            return ResponseEntity.ok(ApiResDto.success(result));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/search")
    @Operation(
            summary = "공중 화장실 카테고리 검색",
            description = "이름, 시도코드, 유형, 장애인시설, 24시간개방으로 공중 화장실을 검색합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletInfo>>> searchByCategory(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiPublicToiletInfoSearchCatReq searchKeys,
            HttpServletRequest request) {

        try {
            PageRes<PoiPublicToiletInfo> searchRet = poiPublicToiletInfoReadService.getPublicToiletsByCategory(searchKeys, page);
            
            log.debug("[{}] : {}", request.getRequestURI(), searchKeys != null ? searchKeys.toString() : "null");
            return ResponseEntity.ok(ApiResDto.success(searchRet));
        } catch (Exception e) {
            log.error("[{}] : {}", request.getRequestURI(), e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/search/location")
    @Operation(
        summary = "공중 화장실 위치 기반 검색",
        description = "위치 기준 반경 내 공중 화장실을 검색합니다."
    )
    public ResponseEntity<ApiResDto<PageRes<PoiPublicToiletInfo>>> searchByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiPublicToiletInfoSearchLocReq searchKeys,
            HttpServletRequest request) {
        
        log.debug("[{}] : {}", request.getRequestURI(), searchKeys.toString());
        
        PageRes<PoiPublicToiletInfo> searchRet = poiPublicToiletInfoReadService.getPublicToiletsByLocation(searchKeys, page);
        
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }

} 