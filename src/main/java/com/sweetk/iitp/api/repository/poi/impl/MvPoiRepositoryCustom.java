package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MvPoiRepositoryCustom {

    List<MvPoi> findAll();
    MvPoiPageResult findAllWithCount(int offset, int size);


    List<MvPoi> findByCategoryAndSubCate(String category, String subCate, String name);
    MvPoiPageResult findByCategoryAndSubCateWithCount(
        String category, String subCate, String name, int offset, int size
    );

    List<MvPoi> findByLocation(
            String category, String name,  BigDecimal latitude, BigDecimal longitude, BigDecimal radius
    );
    MvPoiPageResult findByLocationWithCount(
            BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size
    );

    List<MvPoi> findByCategoryType(String categoryType);
    MvPoiPageResult findByCategoryTypeWithCount( String categoryType, int offset, int size);

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
