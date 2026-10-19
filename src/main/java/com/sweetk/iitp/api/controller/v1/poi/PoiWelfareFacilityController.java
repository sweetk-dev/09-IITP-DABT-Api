package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiWelfareFacility;
import com.sweetk.iitp.api.dto.poi.PoiWelfareFacilitySearchLocReq;
import com.sweetk.iitp.api.dto.poi.PoiWelfareFacilitySearchReq;
import com.sweetk.iitp.api.service.poi.PoiWelfareFacilityReadService;
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

@Tag(name = PoiWelfareFacilityController.TAG_NAME,
        description = "경기도 장애인복지시설 — 장애유형별 거주시설·단기거주시설·보호작업장(정원·현원·종사자), " +
                "중증장애인자립생활지원센터, 장애인 관련 시설(가족지원·인권·발달장애인지원·권익옹호). " +
                "원천: 경기데이터드림(제공 경기도장애인복지종합지원센터), 연 1회 갱신. 원천이 기준일을 주지 않아 " +
                "확인일(checked_dt)을 함께 준다.")
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_WELFARE_FACILITY)
@RequiredArgsConstructor
public class PoiWelfareFacilityController {

    public static final String TAG_NAME = "이동형 - 복지시설 - 장애인복지시설 POI API V1";

    private final PoiWelfareFacilityReadService service;

    @GetMapping("/{facilityId}")
    @Operation(summary = "장애인복지시설 상세 조회", description = "시설 ID로 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResDto<PoiWelfareFacility>> getById(
            @Parameter(description = "시설 ID", required = true) @PathVariable Integer facilityId) {
        return ResponseEntity.ok(ApiResDto.success(service.findById(facilityId)));
    }

    @GetMapping("/search/paging")
    @Operation(summary = "장애인복지시설 검색 (페이징)",
            description = "시설 종류·시군명·세부 구분·시설명으로 검색합니다. 조건이 없으면 전체를 반환합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<PoiWelfareFacility>>> search(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiWelfareFacilitySearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.search(searchKeys, page)));
    }

    @GetMapping("/search/location/paging")
    @Operation(summary = "장애인복지시설 위치 기반 검색 (페이징)",
            description = "위치 기준 반경(미터) 안의 시설을 가까운 순으로 반환합니다. 시설 종류로 좁힐 수 있습니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<PoiWelfareFacility>>> searchByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiWelfareFacilitySearchLocReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchByLocation(searchKeys, page)));
    }
}
