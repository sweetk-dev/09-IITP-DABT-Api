package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.repository.basic.BasicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends BasicRepository<Poi, Long> {
    Page<Poi> findAllByActiveTrue(Pageable pageable);
    Page<Poi> findByType(String type, Pageable pageable);
    Page<Poi> findByNameContaining(String name, Pageable pageable);
    Page<Poi> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable);
} 