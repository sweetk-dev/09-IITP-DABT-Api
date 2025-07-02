package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import org.springframework.stereotype.Repository;

@Repository
public interface MvPoiRepositoryCustom {
    MvPoiPageResult findByCategoryAndSubCateWithCount(
        String category, String subCate, String name, int offset, int size
    );
    MvPoiPageResult findByLocationWithPaging(
        java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.math.BigDecimal radius, int offset, int size
    );


    /*
    Page<MvPoiEntity> findAllByActiveTrue(Pageable pageable);
    Page<MvPoiEntity> findByType(String type, Pageable pageable);
    Page<MvPoiEntity> findByNameContaining(String name, Pageable pageable);
    Page<MvPoiEntity> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable);
     */
} 
