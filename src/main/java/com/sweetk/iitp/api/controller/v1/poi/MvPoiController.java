package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI)
@RequiredArgsConstructor
@Tag(name = "이동형 POI API V1", description = "이동형 API ")
public class MvPoiController {

    private final MvPoiReadService mvPoiReadService;

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