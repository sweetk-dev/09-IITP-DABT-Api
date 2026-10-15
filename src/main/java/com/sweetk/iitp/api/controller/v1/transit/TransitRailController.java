package com.sweetk.iitp.api.controller.v1.transit;

import com.sweetk.iitp.api.dto.poi.PoiBaseSearchLocReq;
import com.sweetk.iitp.api.dto.transit.TransitRailElevator;
import com.sweetk.iitp.api.dto.transit.TransitRailPlatform;
import com.sweetk.iitp.api.dto.transit.TransitRailSearchReq;
import com.sweetk.iitp.api.dto.transit.TransitRailStation;
import com.sweetk.iitp.api.dto.transit.TransitRailToilet;
import com.sweetk.iitp.api.dto.transit.TransitRailWheelchairLift;
import com.sweetk.iitp.api.service.transit.TransitRailReadService;
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

@Tag(name = TransitRailController.TAG_NAME,
        description = "철도 역사의 교통약자 이용 편의 현황과 설비별 상세(엘리베이터·승강장 연단 간격·화장실·휠체어 리프트). " +
                "원천: 한국철도공사 편의시설정보, 국가철도공단 휠체어리프트 현황 등(공공데이터포털). " +
                "역 현황은 코레일 역 전체, 설비별 상세는 수도권 1·4호선 일부 역을 제공한다. 좌표는 일부 역에만 있다.")
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_TRANSIT_RAIL)
@RequiredArgsConstructor
public class TransitRailController {

    public static final String TAG_NAME = "이동형 - 교통 - 철도 역사 편의설비 API V1";

    private final TransitRailReadService service;

    @GetMapping("/stations/{stationId}")
    @Operation(summary = "역 편의 현황 상세 조회", description = "역 ID로 엘리베이터·리프트 수, 경사로·장애인 화장실 여부 등을 조회합니다.")
    public ResponseEntity<ApiResDto<TransitRailStation>> getStation(
            @Parameter(description = "역 ID", required = true) @PathVariable Integer stationId) {
        return ResponseEntity.ok(ApiResDto.success(service.findStation(stationId)));
    }

    @GetMapping("/stations/search/paging")
    @Operation(summary = "역 편의 현황 검색 (페이징)", description = "역명·노선명으로 검색합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitRailStation>>> searchStations(
            @Valid @ParameterObject PageReq page, @Valid @ParameterObject TransitRailSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchStations(searchKeys, page)));
    }

    @GetMapping("/stations/search/location/paging")
    @Operation(summary = "역 편의 현황 위치 기반 검색 (페이징)",
            description = "위치 기준 반경(미터) 안의 역을 가까운 순으로 반환합니다. 좌표가 있는 역만 대상입니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitRailStation>>> searchStationsByLocation(
            @Valid @ParameterObject PageReq page, @Valid @ParameterObject PoiBaseSearchLocReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchStationsByLocation(searchKeys, page)));
    }

    @GetMapping("/elevators/search/paging")
    @Operation(summary = "역 엘리베이터 조회 (페이징)", description = "역명·노선명으로 호기별 위치·정원을 조회합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitRailElevator>>> elevators(
            @Valid @ParameterObject PageReq page, @Valid @ParameterObject TransitRailSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.elevators(searchKeys, page)));
    }

    @GetMapping("/platforms/search/paging")
    @Operation(summary = "역 승강장 조회 (페이징)", description = "역명·노선명으로 승강장별 연단 간격·스크린도어·안전발판을 조회합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitRailPlatform>>> platforms(
            @Valid @ParameterObject PageReq page, @Valid @ParameterObject TransitRailSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.platforms(searchKeys, page)));
    }

    @GetMapping("/toilets/search/paging")
    @Operation(summary = "역 화장실 조회 (페이징)", description = "역명·노선명·장애인 화장실 여부로 개소별 위치를 조회합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitRailToilet>>> toilets(
            @Valid @ParameterObject PageReq page, @Valid @ParameterObject TransitRailSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.toilets(searchKeys, page)));
    }

    @GetMapping("/wheelchair-lifts/search/paging")
    @Operation(summary = "역 휠체어 리프트 조회 (페이징)", description = "역명·노선명으로 리프트 위치·규격을 조회합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<TransitRailWheelchairLift>>> wheelchairLifts(
            @Valid @ParameterObject PageReq page, @Valid @ParameterObject TransitRailSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.wheelchairLifts(searchKeys, page)));
    }
}
