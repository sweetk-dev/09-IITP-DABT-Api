package com.sweetk.iitp.api.controller;

import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.poi.PoiResponse;
import com.sweetk.iitp.api.service.poi.PoiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/poi")
@RequiredArgsConstructor
@Tag(name = "POI API V1", description = "POI 관련 API V1")
public class PoiController {

    private final PoiService poiService;

    @Operation(summary = "POI 검색", description = "키워드로 POI를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResDto<List<PoiResponse>>> searchPoi(
            @Parameter(description = "검색 키워드") @RequestParam String keyword) {
        return ResponseEntity.ok(ApiResDto.success(poiService.searchPoi(keyword)));
    }

    @Operation(summary = "POI 상세 조회", description = "POI ID로 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResDto<PoiResponse>> getPoi(
            @Parameter(description = "POI ID") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResDto.success(poiService.getPoi(id)));
    }

    @Operation(summary = "POI 생성", description = "새로운 POI를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResDto<PoiResponse>> createPoi(
            @Parameter(description = "POI 정보") @RequestBody PoiDto poiDto) {
        return ResponseEntity.ok(ApiResDto.success(poiService.createPoi(poiDto)));
    }

    @Operation(summary = "POI 수정", description = "기존 POI 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResDto<PoiResponse>> updatePoi(
            @Parameter(description = "POI ID") @PathVariable Long id,
            @Parameter(description = "수정할 POI 정보") @RequestBody PoiResponse poiDto) {
        return ResponseEntity.ok(ApiResDto.success(poiService.updatePoi(id, poiDto)));
    }

    @Operation(summary = "POI 삭제", description = "POI를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResDto<Void>> deletePoi(
            @Parameter(description = "POI ID") @PathVariable Long id) {
        poiService.deletePoi(id);
        return ResponseEntity.ok(ApiResDto.success(null));
    }
} 