package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacility;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilityLocation;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilitySearchCatReq;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilitySearchLocReq;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.service.poi.PoiCultureSportsFacilityReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = PoiCultureSportsFacilityController.TAG_NAME,
        description = "점자도서관·책나래서비스 제공도서관·장애인체육시설·장애인체육회 위치 및 연락처 API. " +
                "원천: 경기데이터드림(경기도), 현재 경기도 자료만 제공한다.")
@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI_CULTURE_SPORTS)
@RequiredArgsConstructor
public class PoiCultureSportsFacilityController {

    public static final String TAG_NAME = "이동형 - 문화·체육 - 장애인 문화·체육 시설 POI API V1";

    private final PoiCultureSportsFacilityReadService service;

    @GetMapping("/{facilityId}")
    @Operation(summary = "문화·체육 시설 상세 조회", description = "시설 ID로 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResDto<PoiCultureSportsFacility>> getById(
            @Parameter(description = "시설 ID", required = true) @PathVariable Integer facilityId) {
        PoiCultureSportsFacility f = service.findById(facilityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "facilityId(%d) not found".formatted(facilityId)));
        return ResponseEntity.ok(ApiResDto.success(f));
    }

    @GetMapping("/all")
    @Operation(summary = "전체 문화·체육 시설 조회 (전체 결과)", description = "모든 시설을 종류·이름 순으로 반환합니다.")
    public ResponseEntity<ApiResDto<List<PoiCultureSportsFacility>>> getAll() {
        return ResponseEntity.ok(ApiResDto.success(service.getAll()));
    }

    @GetMapping("/all/paging")
    @Operation(summary = "전체 문화·체육 시설 조회 (페이징)", description = "모든 시설을 종류·이름 순으로 페이징하여 반환합니다.")
    public ResponseEntity<ApiResDto<PageRes<PoiCultureSportsFacility>>> getAllPaging(
            @Valid @ParameterObject PageReq page) {
        return ResponseEntity.ok(ApiResDto.success(service.getAllPaging(page)));
    }

    @GetMapping("/sido/{sidoCode}")
    @Operation(summary = "시도별 문화·체육 시설 조회 (전체 결과)", description = "시도 코드로 시설 목록을 조회합니다.")
    public ResponseEntity<ApiResDto<List<PoiCultureSportsFacility>>> getBySido(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "9410000") @PathVariable String sidoCode) {
        return ResponseEntity.ok(ApiResDto.success(service.getBySido(sidoCode)));
    }

    @GetMapping("/sido/{sidoCode}/paging")
    @Operation(summary = "시도별 문화·체육 시설 조회 (페이징)", description = "시도 코드로 시설 목록을 페이징하여 조회합니다.")
    public ResponseEntity<ApiResDto<PageRes<PoiCultureSportsFacility>>> getBySidoPaging(
            @Parameter(description = "시도 코드 (7자리)", required = true, example = "9410000") @PathVariable String sidoCode,
            @Valid @ParameterObject PageReq page) {
        return ResponseEntity.ok(ApiResDto.success(service.getBySidoPaging(sidoCode, page)));
    }

    @GetMapping("/search")
    @Operation(summary = "문화·체육 시설 카테고리 검색 (전체 결과)",
            description = "시설 종류, 이름, 시군명, 시도 코드로 검색합니다. 조건이 하나 이상 있어야 합니다.")
    public ResponseEntity<ApiResDto<List<PoiCultureSportsFacility>>> search(
            @Valid @ParameterObject PoiCultureSportsFacilitySearchCatReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.search(searchKeys)));
    }

    @GetMapping("/search/paging")
    @Operation(summary = "문화·체육 시설 카테고리 검색 (페이징)",
            description = "시설 종류, 이름, 시군명, 시도 코드로 검색합니다. 조건이 하나 이상 있어야 합니다. 페이징 처리된 결과를 반환합니다.")
    public ResponseEntity<ApiResDto<PageRes<PoiCultureSportsFacility>>> searchPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiCultureSportsFacilitySearchCatReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchPaging(searchKeys, page)));
    }

    @GetMapping("/search/location")
    @Operation(summary = "문화·체육 시설 위치 기반 검색 (전체 결과)",
            description = "위치 기준 반경(미터) 안의 시설을 가까운 순으로 반환합니다. 시설 종류·이름으로 좁힐 수 있습니다.")
    public ResponseEntity<ApiResDto<List<PoiCultureSportsFacilityLocation>>> searchByLocation(
            @Valid @ParameterObject PoiCultureSportsFacilitySearchLocReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchByLocation(searchKeys)));
    }

    @GetMapping("/search/location/paging")
    @Operation(summary = "문화·체육 시설 위치 기반 검색 (페이징)",
            description = "위치 기준 반경(미터) 안의 시설을 가까운 순으로 페이징하여 반환합니다. 시설 종류·이름으로 좁힐 수 있습니다.")
    public ResponseEntity<ApiResDto<PageRes<PoiCultureSportsFacilityLocation>>> searchByLocationPaging(
            @Valid @ParameterObject PageReq page,
            @Valid @ParameterObject PoiCultureSportsFacilitySearchLocReq searchKeys) {
        return ResponseEntity.ok(ApiResDto.success(service.searchByLocationPaging(searchKeys, page)));
    }
}
