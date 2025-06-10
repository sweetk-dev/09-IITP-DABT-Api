package com.sweetk.iitp.api.controller.v1.poi;

import com.sweetk.iitp.api.constant.ApiConstants;
import com.sweetk.iitp.api.service.poi.MvPoiReadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ApiConstants.ApiPath.API_V1_POI)
@RequiredArgsConstructor
@Tag(name = "이동형 POI API V1", description = "이동형 API ")
public class MvPoiController {

    private final MvPoiReadService mvPoiReadService;

    /*
    @GetMapping
    @Operation (
            summary = "이동형 POI 조회",
            description = "이동형 POI 조회 (paging)"
    )
    public ResponseEntity<ApiResDto><PageResponse<PoiResponse>>> getAllPois (@RequestParam String lang,  @Valid PageRequest pageRequest) {

        log.debug("POI 전체 목록 조회 요청 - 페이지: {}, 크기: {}, 정렬: {}",
                pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSortBy());
        return ResponseEntity.ok(ApiResDto.success(response));
    }
*/
    /*
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'CLIENT')")
    @Operation(
        summary = "Get POI by ID",
        description = "Retrieves a specific POI by its ID"
    )
    @ApiResponses(value = {
        @ApiResDto(responseCode = "200", description = "Successfully retrieved POI"),
        @ApiResDto(responseCode = "404", description = "POI not found"),
        @ApiResDto(responseCode = "401", description = "Unauthorized"),
        @ApiResDto(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ApiResDto<PoiResponse>> getPoiById(
            @Parameter(description = "ID of the POI to retrieve", required = true)
            @PathVariable Long id) {
        log.info("POI 상세 조회 요청 - ID: {}", id);
        
        try {
            return poiService.findById(id)
                    .map(poi -> {
                        log.debug("POI 상세 조회 성공 - ID: {}, 이름: {}", id, poi.getName());
                        return ResponseEntity.ok(ApiResDto.success(PoiConverter.toResponse(poi)));
                    })
                    .orElseGet(() -> {
                        log.warn("POI 상세 조회 실패 - ID: {} (존재하지 않음)", id);
                        return ResponseEntity.ok(ApiResDto.error("POI not found", "POI_NOT_FOUND"));
                    });
        } catch (Exception e) {
            log.error("POI 상세 조회 중 오류 발생 - ID: {}, 에러: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'CLIENT')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'CLIENT')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'CLIENT')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Create a new POI",
        description = "Creates a new Point of Interest"
    )
    @ApiResponses(value = {
        @ApiResDto(responseCode = "200", description = "Successfully created POI"),
        @ApiResDto(responseCode = "400", description = "Invalid input"),
        @ApiResDto(responseCode = "401", description = "Unauthorized"),
        @ApiResDto(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ApiResDto<PoiResponse>> createPoi(
            @Valid @RequestBody PoiRequest request) {
        log.info("POI 생성 요청 - 이름: {}, 타입: {}", request.getName(), request.getType());
        
        try {
            Poi poi = PoiConverter.toEntity(request);
            Poi savedPoi = poiService.save(poi);
            log.info("POI 생성 성공 - ID: {}, 이름: {}", savedPoi.getId(), savedPoi.getName());
            return ResponseEntity.ok(ApiResDto.success(PoiConverter.toResponse(savedPoi), "POI created successfully"));
        } catch (Exception e) {
            log.error("POI 생성 실패 - 이름: {}, 에러: {}", request.getName(), e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update an existing POI",
        description = "Updates an existing Point of Interest by ID"
    )
    @ApiResponses(value = {
        @ApiResDto(responseCode = "200", description = "Successfully updated POI"),
        @ApiResDto(responseCode = "400", description = "Invalid input"),
        @ApiResDto(responseCode = "404", description = "POI not found"),
        @ApiResDto(responseCode = "401", description = "Unauthorized"),
        @ApiResDto(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ApiResDto<PoiResponse>> updatePoi(
            @Parameter(description = "ID of the POI to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody PoiRequest request) {
        log.info("POI 수정 요청 - ID: {}, 이름: {}, 타입: {}", 
                id, request.getName(), request.getType());
        
        try {
            Poi poi = PoiConverter.toEntity(request);
            Poi updatedPoi = poiService.update(id, poi);
            log.info("POI 수정 성공 - ID: {}, 이름: {}", id, updatedPoi.getName());
            return ResponseEntity.ok(ApiResDto.success(PoiConverter.toResponse(updatedPoi), "POI updated successfully"));
        } catch (Exception e) {
            log.error("POI 수정 실패 - ID: {}, 에러: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Delete a POI",
        description = "Permanently deletes a Point of Interest by ID"
    )
    @ApiResponses(value = {
        @ApiResDto(responseCode = "200", description = "Successfully deleted POI"),
        @ApiResDto(responseCode = "404", description = "POI not found"),
        @ApiResDto(responseCode = "401", description = "Unauthorized"),
        @ApiResDto(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<ApiResDto<Void>> deletePoi(
            @Parameter(description = "ID of the POI to delete", required = true)
            @PathVariable Long id) {
        log.info("POI 삭제 요청 - ID: {}", id);
        
        try {
            poiService.delete(id);
            log.info("POI 삭제 성공 - ID: {}", id);
            return ResponseEntity.ok(ApiResDto.success(null, "POI deleted successfully"));
        } catch (Exception e) {
            log.error("POI 삭제 실패 - ID: {}, 에러: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}/soft")
    @PreAuthorize("hasRole('ADMIN')")
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

     */
} 