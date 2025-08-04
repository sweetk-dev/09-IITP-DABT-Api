package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.entity.poi.PoiTourBfFacilityEntity;
import com.sweetk.iitp.api.repository.poi.impl.PoiTourBfFacilityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiTourBfFacilityRepository extends JpaRepository<PoiTourBfFacilityEntity, Integer>, PoiTourBfFacilityRepositoryCustom {
} 