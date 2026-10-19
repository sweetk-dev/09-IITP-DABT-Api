package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.dto.poi.PoiBuildingAccessibility;
import com.sweetk.iitp.api.dto.poi.PoiBuildingAccessibilitySearchLocReq;
import com.sweetk.iitp.api.dto.poi.PoiBuildingAccessibilitySearchReq;
import com.sweetk.iitp.api.service.poi.PoiBuildingAccessibilityReadService;
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

@Tag(name = PoiBuildingAccessibilityController.TAG_NAME,
        description = "건물별 장애인편의시설 설치 현황(승강기·장애인 화장실·전용 주차구역·주출입구 경사로 등). " +
                "원천: 한국사회보장정보원 장애인편의시설 현황(공공데이터포털). 현재 경기도 안양시 자료를 제공한다.")
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_BUILDING_ACCESSIBILITY)
@RequiredArgsConstructor
public class PoiBuildingAccessibilityController {

    public static final String TAG_NAME = "이동형 - 편의시설 - 건물 장애인편의시설 POI API V1";

    private final PoiBuildingAccessibilityReadService service;

    @GetMapping("/{faclId}")
    @Operation(summary = "건물 장애인편의시설 상세 조회", description = "시설 ID로 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResDto<PoiBuildingAccessibility>> getById(
            @Parameter(description = "시설 ID", required = true) @PathVariable Integer faclId) {
        return ResponseEntity.ok(ApiResDto.success(service.findById(faclId)));
    }

    @GetMapping("/search/paging")
    @Operation(summary = "건물 장애인편의시설 검색 (페이징)",
            description = "시설명·유형·주소·편의시설 설치 여부로 검색합니다. 조건이 없으면 전체를 시설명 순으로 반환합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<PoiBuildingAccessibility>>> search(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiBuildingAccessibilitySearchReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.search(searchKeys, page)));
    }

    @GetMapping("/search/location/paging")
    @Operation(summary = "건물 장애인편의시설 위치 기반 검색 (페이징)",
            description = "위치 기준 반경(미터) 안의 시설을 가까운 순으로 반환합니다. 페이징(page 0부터, size 2~1000).")
    public ResponseEntity<ApiResDto<PageRes<PoiBuildingAccessibility>>> searchByLocation(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiBuildingAccessibilitySearchLocReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchByLocation(searchKeys, page)));
    }
}
