package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchCatReq;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchLocReq;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MvPoiRepositoryCustom {
    PageRes<MvPoi> findPoiList(Pageable pageable);
    PageRes<MvPoi> findByCategory(MvPoiSearchCatReq catReq, Pageable pageable);
    PageRes<MvPoi> findByLocation(MvPoiSearchLocReq locReq, Pageable pageable);


    /*
    Page<MvPoiEntity> findAllByActiveTrue(Pageable pageable);
    Page<MvPoiEntity> findByType(String type, Pageable pageable);
    Page<MvPoiEntity> findByNameContaining(String name, Pageable pageable);
    Page<MvPoiEntity> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable);
     */
} 
