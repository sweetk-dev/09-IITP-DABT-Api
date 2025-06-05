package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.dto.common.ApiResDto;
import com.sweetk.iitp.api.dto.common.PageRequest;
import com.sweetk.iitp.api.dto.common.PageResponse;
import com.sweetk.iitp.api.dto.poi.PoiRequest;
import com.sweetk.iitp.api.dto.poi.PoiResponse;
import com.sweetk.iitp.api.dto.poi.PoiSearchRequest;
import com.sweetk.iitp.api.dto.poi.converter.PoiConverter;
import com.sweetk.iitp.api.service.poi.PoiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI)
@RequiredArgsConstructor
@Tag(name = "POI API V1", description = "POI OpenApi V1")
public class PoiController {

    private final PoiService poiService;

    @GetMapping
    @Operation(
        summary = "Get all POIs",
        description = "Retrieves a paginated list of all active POIs"
    )
    @ApiResponses(value = {
        @ApiResDto(responseCode = "200", description = "Successfully retrieved POIs"),
        @ApiResDto(responseCode = "401", description = "Unauthorized"),
        @ApiResDto(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ApiResDto<PageResponse<PoiResponse>>> getAllPois(
            @Valid PageRequest pageRequest) {
        log.info("POI 전체 목록 조회 요청 - 페이지: {}, 크기: {}, 정렬: {}", 
                pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSortBy());
        
        try {
            PageRequest springPageRequest = PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                Sort.by(Sort.Direction.fromString(
                    pageRequest.getSortDirection() != null ? pageRequest.getSortDirection() : "ASC"),
                    pageRequest.getSortBy() != null ? pageRequest.getSortBy() : "id"
                )
            );

            Page<Poi> poiPage = poiService.findAll(springPageRequest);
            PageResponse<PoiResponse> response = PageResponse.<PoiResponse>builder()
                    .content(poiPage.getContent().stream()
                            .map(PoiConverter::toResponse)
                            .collect(Collectors.toList()))
                    .page(poiPage.getNumber())
                    .size(poiPage.getSize())
                    .totalElements(poiPage.getTotalElements())
                    .totalPages(poiPage.getTotalPages())
                    .first(poiPage.isFirst())
                    .last(poiPage.isLast())
                    .empty(poiPage.isEmpty())
                    .build();

            log.debug("POI 전체 목록 조회 결과 - 총 개수: {}, 페이지 수: {}", 
                    poiPage.getTotalElements(), poiPage.getTotalPages());
            return ResponseEntity.ok(ApiResDto.success(response));
            
        } catch (Exception e) {
            log.error("POI 전체 목록 조회 실패 - 에러: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "POI 상세 정보 조회", description = "POI ID로 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResDto> getPoi(@PathVariable Long id) {
        PoiResponse poi = poiService.getPoi(id);
        return ResponseEntity.ok(ApiResDto.success(poi));
    }

    @GetMapping("/type/{type}")
    @Operation(
        summary = "Get POIs by type",
        description = "Retrieves a paginated list of POIs filtered by type"
    )
    public ResponseEntity<ApiResDto<PageResponse<PoiResponse>>> getPoisByType(
            @Parameter(description = "Type of POIs to retrieve", required = true)
            @PathVariable String type,
            @Valid PageRequest pageRequest) {
        log.info("POI 타입별 조회 요청 - 타입: {}, 페이지: {}, 크기: {}", 
                type, pageRequest.getPage(), pageRequest.getSize());
        
        try {
            PageRequest springPageRequest = PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                Sort.by(Sort.Direction.fromString(
                    pageRequest.getSortDirection() != null ? pageRequest.getSortDirection() : "ASC"),
                    pageRequest.getSortBy() != null ? pageRequest.getSortBy() : "id"
                )
            );

            Page<Poi> poiPage = poiService.findByType(type, springPageRequest);
            PageResponse<PoiResponse> response = PageResponse.<PoiResponse>builder()
                    .content(poiPage.getContent().stream()
                            .map(PoiConverter::toResponse)
                            .collect(Collectors.toList()))
                    .page(poiPage.getNumber())
                    .size(poiPage.getSize())
                    .totalElements(poiPage.getTotalElements())
                    .totalPages(poiPage.getTotalPages())
                    .first(poiPage.isFirst())
                    .last(poiPage.isLast())
                    .empty(poiPage.isEmpty())
                    .build();

            log.debug("POI 타입별 조회 결과 - 타입: {}, 총 개수: {}, 페이지 수: {}", 
                    type, poiPage.getTotalElements(), poiPage.getTotalPages());
            return ResponseEntity.ok(ApiResDto.success(response));
            
        } catch (Exception e) {
            log.error("POI 타입별 조회 실패 - 타입: {}, 에러: {}", type, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    @Operation(
        summary = "Search POIs by name",
        description = "Retrieves a paginated list of POIs filtered by name"
    )
    public ResponseEntity<ApiResDto<PageResponse<PoiResponse>>> searchPois(
            @Parameter(description = "Name to search for", required = true)
            @RequestParam String name,
            @Valid PageRequest pageRequest) {
        log.info("POI 이름 검색 요청 - 검색어: {}, 페이지: {}, 크기: {}", 
                name, pageRequest.getPage(), pageRequest.getSize());
        
        try {
            PageRequest springPageRequest = PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                Sort.by(Sort.Direction.fromString(
                    pageRequest.getSortDirection() != null ? pageRequest.getSortDirection() : "ASC"),
                    pageRequest.getSortBy() != null ? pageRequest.getSortBy() : "id"
                )
            );

            Page<Poi> poiPage = poiService.findByNameContaining(name, springPageRequest);
            PageResponse<PoiResponse> response = PageResponse.<PoiResponse>builder()
                    .content(poiPage.getContent().stream()
                            .map(PoiConverter::toResponse)
                            .collect(Collectors.toList()))
                    .page(poiPage.getNumber())
                    .size(poiPage.getSize())
                    .totalElements(poiPage.getTotalElements())
                    .totalPages(poiPage.getTotalPages())
                    .first(poiPage.isFirst())
                    .last(poiPage.isLast())
                    .empty(poiPage.isEmpty())
                    .build();

            log.debug("POI 이름 검색 결과 - 검색어: {}, 총 개수: {}, 페이지 수: {}", 
                    name, poiPage.getTotalElements(), poiPage.getTotalPages());
            return ResponseEntity.ok(ApiResDto.success(response));
            
        } catch (Exception e) {
            log.error("POI 이름 검색 실패 - 검색어: {}, 에러: {}", name, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/nearby")
    @Operation(
        summary = "Find POIs within radius",
        description = "Retrieves a paginated list of POIs within a specified radius from a location"
    )
    public ResponseEntity<ApiResDto<PageResponse<PoiResponse>>> findNearbyPois(
            @Valid @RequestBody PoiSearchRequest request,
            @Valid PageRequest pageRequest) {
        log.info("주변 POI 검색 요청 - 위도: {}, 경도: {}, 반경: {}km, 페이지: {}", 
                request.getLatitude(), request.getLongitude(), request.getRadius(), 
                pageRequest.getPage());
        
        try {
            PageRequest springPageRequest = PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize(),
                Sort.by(Sort.Direction.fromString(
                    pageRequest.getSortDirection() != null ? pageRequest.getSortDirection() : "ASC"),
                    pageRequest.getSortBy() != null ? pageRequest.getSortBy() : "id"
                )
            );

            Page<Poi> poiPage = poiService.findByLocationWithinRadius(
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getRadius(),
                    springPageRequest
            );

            PageResponse<PoiResponse> response = PageResponse.<PoiResponse>builder()
                    .content(poiPage.getContent().stream()
                            .map(PoiConverter::toResponse)
                            .collect(Collectors.toList()))
                    .page(poiPage.getNumber())
                    .size(poiPage.getSize())
                    .totalElements(poiPage.getTotalElements())
                    .totalPages(poiPage.getTotalPages())
                    .first(poiPage.isFirst())
                    .last(poiPage.isLast())
                    .empty(poiPage.isEmpty())
                    .build();

            log.debug("주변 POI 검색 결과 - 총 개수: {}, 페이지 수: {}", 
                    poiPage.getTotalElements(), poiPage.getTotalPages());
            return ResponseEntity.ok(ApiResDto.success(response));
            
        } catch (Exception e) {
            log.error("주변 POI 검색 실패 - 에러: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @Operation(summary = "POI 등록", description = "새로운 POI를 등록합니다.")
    public ResponseEntity<ApiResDto> createPoi(@Valid @RequestBody PoiRequest request) {
        PoiResponse poi = poiService.createPoi(request);
        return ResponseEntity.ok(ApiResDto.success(poi));
    }

    @PutMapping("/{id}")
    @Operation(summary = "POI 수정", description = "기존 POI 정보를 수정합니다.")
    public ResponseEntity<ApiResDto> updatePoi(@PathVariable Long id, @Valid @RequestBody PoiRequest request) {
        PoiResponse poi = poiService.updatePoi(id, request);
        return ResponseEntity.ok(ApiResDto.success(poi));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "POI 삭제", description = "POI를 삭제합니다.")
    public ResponseEntity<ApiResDto> deletePoi(@PathVariable Long id) {
        poiService.deletePoi(id);
        return ResponseEntity.ok(ApiResDto.success());
    }

    @DeleteMapping("/{id}/soft")
    @Operation(
        summary = "Soft delete a POI",
        description = "Marks a Point of Interest as inactive by ID"
    )
    @ApiResponses(value = {
        @ApiResDto(responseCode = "200", description = "Successfully soft deleted POI"),
        @ApiResDto(responseCode = "404", description = "POI not found"),
        @ApiResDto(responseCode = "401", description = "Unauthorized"),
        @ApiResDto(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ApiResDto<Void>> softDeletePoi(
            @Parameter(description = "ID of the POI to soft delete", required = true)
            @PathVariable Long id) {
        log.info("POI 소프트 삭제 요청 - ID: {}", id);
        
        try {
            poiService.softDelete(id);
            log.info("POI 소프트 삭제 성공 - ID: {}", id);
            return ResponseEntity.ok(ApiResDto.success(null, "POI soft deleted successfully"));
        } catch (Exception e) {
            log.error("POI 소프트 삭제 실패 - ID: {}, 에러: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 