package com.sweetk.iitp.api.controller.v1.poi;


import com.sweetk.iitp.api.constant.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_BF_TOUR)
@RequiredArgsConstructor
@Tag(name = "편의시설 - 무장애 관광지 시설", description = "편의시설 -무장애(Barrier Free) 관광지 시설 관련 API")
public class PoiBarrierFreeTourController {
//
//    @GetMapping("/search")
//    @Operation(
//            summary = "무장애 관광지 시설 검색",
//            description = "다양한 조건으로 무장애 관광지 시설을 검색합니다."
//    )
//    public ResponseEntity<ApiResDto<Page<PoiTourBfFacility>>> searchTourBfFacilities(
//            @Parameter(description = "검색 조건")
//            @Valid @ParameterObject PageReq page,
//            @ParameterObject PoiTourBfFacilitySearchReq searchReq,
//            HttpServletRequest request) {
//
//        log.info("무장애 관광지 시설 검색 요청 - {}", searchReq);
//
//        Page<PoiTourBfFacility> result = poiTourBfFacilityReadService.search(searchReq);
//        return ResponseEntity.ok(ApiResDto.success(result));
//    }

}
