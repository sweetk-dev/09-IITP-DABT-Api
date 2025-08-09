package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiLocation;
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
    PoiPageResult findAllWithPagingCount(int offset, int size);


    List<MvPoi> findByCategoryType(String categoryType);
    PoiPageResult findByCategoryTypeWithPagingCount(String categoryType, int offset, int size);


    List<MvPoi> findByCategoryAndSubCate(String category, String subCate, String name);
    PoiPageResult findByCategoryAndSubCateWithPagingCount(
        String category, String subCate, String name, int offset, int size
    );



    // 거리 정보를 포함한 위치 기반 검색
    List<MvPoiLocation> findByLocationWithConditions(
            String category, String name, BigDecimal latitude, BigDecimal longitude, BigDecimal radius
    );
    PoiPageResult<MvPoiLocation> findByLocationWithConditionsAndPagingCount(
            String category, String name, BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size
    );

} 
