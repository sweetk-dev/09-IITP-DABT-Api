package com.sweetk.iitp.api.controller.v1.transit;

import com.sweetk.iitp.api.dto.poi.PoiBaseSearchLocReq;
import com.sweetk.iitp.api.dto.transit.TransitBusRoute;
import com.sweetk.iitp.api.dto.transit.TransitBusRouteSearchReq;
import com.sweetk.iitp.api.dto.transit.TransitBusRouteStop;
import com.sweetk.iitp.api.dto.transit.TransitBusStation;
import com.sweetk.iitp.api.dto.transit.TransitBusStationSearchReq;
import com.sweetk.iitp.api.service.transit.TransitBusReadService;
import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = TransitBusController.TAG_NAME,
        description = "경기 버스 노선·정류소·노선별 경유 정류소와 노선별 저상버스 운행 여부. " +
                "원천: 경기도 버스노선·정류소 조회(공공데이터포털). 현재 안양시를 지나는 노선과 그 경유 정류소를 제공한다.")
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_TRANSIT_BUS)
@RequiredArgsConstructor
public class TransitBusController {

    public static final String TAG_NAME = "이동형 - 교통 - 경기 버스 노선·정류소 API V1";

    private final TransitBusReadService service;

    @GetMapping("/routes/{routeId}")
    @Operation(summary = "버스 노선 상세 조회", description = "노선 ID로 배차·첫막차·저상버스 운행 여부를 조회합니다.")
    public ResponseEntity<ApiResDto<TransitBusRoute>> getRoute(
            @Parameter(description = "노선 ID", required = true) @PathVariable Long routeId) {
        return ResponseEntity.ok(ApiResDto.success(service.findRoute(routeId)));
    }

    @GetMapping("/routes/{routeId}/stations")
    @Operation(summary = "노선 경유 정류소 조회", description = "노선이 경유하는 정류소를 경유 순서대로 반환합니다.")
    public ResponseEntity<ApiResDto<List<TransitBusRouteStop>>> getRouteStations(
            @Parameter(description = "노선 ID", required = true) @PathVariable Long routeId) {
        return ResponseEntity.ok(ApiResDto.success(service.findRouteStops(routeId)));
    }

    @GetMapping("/routes/search/paging")
    @Operation(summary = "버스 노선 검색 (페이징)",
            description = "노선 번호·운행 지역·저상버스 운행 여부로 검색합니다. 조건이 없으면 전체를 반환합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitBusRoute>>> searchRoutes(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject TransitBusRouteSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchRoutes(searchKeys, page)));
    }

    @GetMapping("/stations/{stationId}")
    @Operation(summary = "버스 정류소 상세 조회", description = "정류소 ID로 조회합니다.")
    public ResponseEntity<ApiResDto<TransitBusStation>> getStation(
            @Parameter(description = "정류소 ID", required = true) @PathVariable Long stationId) {
        return ResponseEntity.ok(ApiResDto.success(service.findStation(stationId)));
    }

    @GetMapping("/stations/{stationId}/routes")
    @Operation(summary = "정류소 경유 노선 조회", description = "정류소를 지나는 노선을 저상버스 운행 여부와 함께 반환합니다.")
    public ResponseEntity<ApiResDto<List<TransitBusRoute>>> getStationRoutes(
            @Parameter(description = "정류소 ID", required = true) @PathVariable Long stationId) {
        return ResponseEntity.ok(ApiResDto.success(service.findStationRoutes(stationId)));
    }

    @GetMapping("/stations/search/paging")
    @Operation(summary = "버스 정류소 검색 (페이징)",
            description = "정류소명·정류소 번호·지역명으로 검색합니다. 조건이 없으면 전체를 반환합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitBusStation>>> searchStations(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject TransitBusStationSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchStations(searchKeys, page)));
    }

    @GetMapping("/stations/search/location/paging")
    @Operation(summary = "버스 정류소 위치 기반 검색 (페이징)",
            description = "위치 기준 반경(미터) 안의 정류소를 가까운 순으로 반환합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitBusStation>>> searchStationsByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiBaseSearchLocReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchStationsByLocation(searchKeys, page)));
    }
}
