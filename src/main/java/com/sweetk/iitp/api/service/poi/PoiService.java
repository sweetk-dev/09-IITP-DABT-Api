package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PoiService extends BaseService<Poi, Long> {
    Page<Poi> findAll(Pageable pageable);
    Page<Poi> findByType(String type, Pageable pageable);
    Page<Poi> findByNameContaining(String name, Pageable pageable);
    Page<Poi> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable);
} 