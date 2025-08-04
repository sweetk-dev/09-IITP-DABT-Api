package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MvPoiRepositoryCustom {
    MvPoiPageResult findByCategoryAndSubCateWithCount(
        String category, String subCate, String name, int offset, int size
    );
    MvPoiPageResult findByLocationWithPaging(
        java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.math.BigDecimal radius, int offset, int size
    );
    MvPoiPageResult findByCategoryTypeWithCount(
        String categoryType, int offset, int size
    );

    /**
     * POI ID로 상세 정보 조회 (JSON에서 카테고리 정보 추출)
     */
    Optional<MvPoi> findByIdWithCategory(Long poiId);


    /*
    Page<MvPoiEntity> findAllByActiveTrue(Pageable pageable);
    Page<MvPoiEntity> findByType(String type, Pageable pageable);
    Page<MvPoiEntity> findByNameContaining(String name, Pageable pageable);
    Page<MvPoiEntity> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable);
     */
} 
