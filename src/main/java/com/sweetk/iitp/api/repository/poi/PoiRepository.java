package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.entity.poi.PoiEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends BasicRepository<PoiEntity, Long> {
    Page<PoiEntity> findAllByActiveTrue(Pageable pageable);
    Page<PoiEntity> findByType(String type, Pageable pageable);
    Page<PoiEntity> findByNameContaining(String name, Pageable pageable);
    Page<PoiEntity> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable);
} 
