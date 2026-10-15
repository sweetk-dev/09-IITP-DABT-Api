package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.entity.poi.PoiCultureSportsFacilityEntity;
import com.sweetk.iitp.api.repository.poi.impl.PoiCultureSportsFacilityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiCultureSportsFacilityRepository
        extends JpaRepository<PoiCultureSportsFacilityEntity, Integer>, PoiCultureSportsFacilityRepositoryCustom {
}
