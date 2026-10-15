package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.dto.poi.PoiEmergencySupport;
import com.sweetk.iitp.api.dto.poi.PoiEmergencySupportSearchLocReq;
import com.sweetk.iitp.api.dto.poi.PoiEmergencySupportSearchReq;
import com.sweetk.iitp.api.service.poi.PoiEmergencySupportReadService;
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

@Tag(name = PoiEmergencySupportController.TAG_NAME,
        description = "이동 중 긴급 상황에 찾는 지원 거점 — 전동보장구 급속충전기(전국), 보조기기 수리·지원 센터(경기), " +
                "교통약자 이동지원센터. 원천: 전국전동휠체어급속충전기표준데이터(공공데이터포털), " +
                "경기도 시군별 보조기기서비스센터 현황(경기데이터드림). 원천 기준일이 행마다 다르므로 base_dt 를 확인한다.")
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_EMERGENCY_SUPPORT)
@RequiredArgsConstructor
public class PoiEmergencySupportController {

    public static final String TAG_NAME = "이동형 - 긴급대응 - 충전·수리 지원 거점 POI API V1";

    private final PoiEmergencySupportReadService service;

    @GetMapping("/{supportId}")
    @Operation(summary = "지원 거점 상세 조회", description = "거점 ID로 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResDto<PoiEmergencySupport>> getById(
            @Parameter(description = "거점 ID", required = true) @PathVariable Integer supportId) {
        return ResponseEntity.ok(ApiResDto.success(service.findById(supportId)));
    }

    @GetMapping("/search/paging")
    @Operation(summary = "지원 거점 검색 (페이징)",
            description = "거점 종류·시도 코드·시군구명·거점명으로 검색합니다. 조건이 없으면 전체를 반환합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<PoiEmergencySupport>>> search(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiEmergencySupportSearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.search(searchKeys, page)));
    }

    @GetMapping("/search/location/paging")
    @Operation(summary = "지원 거점 위치 기반 검색 (페이징)",
            description = "위치 기준 반경(미터) 안의 거점을 가까운 순으로 반환합니다. 거점 종류로 좁힐 수 있습니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<PoiEmergencySupport>>> searchByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiEmergencySupportSearchLocReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchByLocation(searchKeys, page)));
    }
}
