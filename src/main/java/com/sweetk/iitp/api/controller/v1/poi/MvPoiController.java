package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchLocReq;
import com.sweetk.iitp.api.service.poi.MvPoiReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI)
@RequiredArgsConstructor
@Tag(name = "이동형 POI API V1", description = "이동형 API ")
public class MvPoiController {

    private final MvPoiReadService mvPoiReadService;

    @GetMapping("/search")
    @Operation(
            summary = "이동형 POI 검색 조회",
            description = "이동형 POI 검색 조회 (paging):"
    )
    public ResponseEntity<ApiResDto<PageRes<MvPoi>>> searchByCategory
            (@Valid @RequestBody MvPoiSearchLocReq searchLocReq,
             HttpServletRequest request) {

        PageRes<MvPoi> searchRet = null;
        log.debug("[{}] : {}", request.getRequestURI(), searchLocReq.toString() );
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }

//    public List<MvPoiRes> search(MvPoiSearchReq request) {
//        return switch (request.getSearchType()) {
//            case CATEGORY -> searchByCategory(request);
//            case LOCATION -> searchByLocation(request);
//        };
//    }

    @GetMapping("/search/location")
    @Operation(
            summary = "이동형 POI 위치기반 검색 조회",
            description = "이동형 POI 위치기반 검색 조회 (paging):"
    )
    public ResponseEntity<ApiResDto<PageRes<MvPoi>>> searchByLocation
            (@Valid @RequestBody MvPoiSearchLocReq searchLocReq,
             HttpServletRequest request ) {

        PageRes<MvPoi> searchRet = null;
        log.debug("[{}] : {}", request.getRequestURI(), searchLocReq.toString() );
        return ResponseEntity.ok(ApiResDto.success(searchRet));
    }
} 