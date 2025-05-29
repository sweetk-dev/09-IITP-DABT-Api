package com.sweetk.iitp.api.controller.V2;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.PoiDto;
import com.sweetk.iitp.api.service.poi.PoiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/poi")
@RequiredArgsConstructor
@Tag(name = "POI API V2", description = "POI 관련 API V2")
public class PoiController {

    private final PoiService poiService;

    @Operation(summary = "POI 검색", description = "키워드로 POI를 검색합니다. (V2)")
    @GetMapping("/search")
    public ResponseEntity<ApiResDto<List<PoiDto>>> searchPoi(
            @Parameter(description = "검색 키워드") @RequestParam String keyword,
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResDto.success(poiService.searchPoiV2(keyword, page, size)));
    }

    @Operation(summary = "POI 상세 조회", description = "POI ID로 상세 정보를 조회합니다. (V2)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResDto<PoiDto>> getPoi(
            @Parameter(description = "POI ID") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResDto.success(poiService.getPoiV2(id)));
    }

    @Operation(summary = "POI 생성", description = "새로운 POI를 생성합니다. (V2)")
    @PostMapping
    public ResponseEntity<ApiResDto<PoiDto>> createPoi(
            @Parameter(description = "POI 정보") @RequestBody PoiDto poiDto) {
        return ResponseEntity.ok(ApiResDto.success(poiService.createPoiV2(poiDto)));
    }

    @Operation(summary = "POI 수정", description = "기존 POI 정보를 수정합니다. (V2)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResDto<PoiDto>> updatePoi(
            @Parameter(description = "POI ID") @PathVariable Long id,
            @Parameter(description = "수정할 POI 정보") @RequestBody PoiDto poiDto) {
        return ResponseEntity.ok(ApiResDto.success(poiService.updatePoiV2(id, poiDto)));
    }

    @Operation(summary = "POI 삭제", description = "POI를 삭제합니다. (V2)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResDto<Void>> deletePoi(
            @Parameter(description = "POI ID") @PathVariable Long id) {
        poiService.deletePoiV2(id);
        return ResponseEntity.ok(ApiResDto.success(null));
    }
} 