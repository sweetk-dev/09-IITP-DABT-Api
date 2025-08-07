package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MvPoiRepositoryCustom {

    /**
     * POI ID로 상세 정보 조회
     */
    Optional<MvPoi> findByIdWithPublished(Long poiId);


    List<MvPoi> findAllPublished();
    MvPoiPageResult findAllWithPagingCount(int offset, int size);


    List<MvPoi> findByCategoryType(String categoryType);
    MvPoiPageResult findByCategoryTypeWithPagingCount( String categoryType, int offset, int size);


    List<MvPoi> findByCategoryAndSubCate(String category, String subCate, String name);
    MvPoiPageResult findByCategoryAndSubCateWithPagingCount(
        String category, String subCate, String name, int offset, int size
    );

    List<MvPoi> findByLocation(
            String category, String name,  BigDecimal latitude, BigDecimal longitude, BigDecimal radius
    );
    MvPoiPageResult findByLocationWithPagingCount(
            String category, String name, BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size
    );

} 
