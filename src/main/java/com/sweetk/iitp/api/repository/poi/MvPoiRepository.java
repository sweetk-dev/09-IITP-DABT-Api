package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.entity.poi.MvPoiEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MvPoiRepository extends BasicRepository<MvPoiEntity, Long> {
    Page<MvPoiEntity> findAllByActiveTrue(Pageable pageable);
    Page<MvPoiEntity> findByType(String type, Pageable pageable);
    Page<MvPoiEntity> findByNameContaining(String name, Pageable pageable);
    Page<MvPoiEntity> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable);
} 
