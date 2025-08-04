package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.entity.poi.PoiSubwayElevatorEntity;
import com.sweetk.iitp.api.repository.poi.impl.PoiSubwayElevatorRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiSubwayElevatorRepository extends JpaRepository<PoiSubwayElevatorEntity, Integer>, PoiSubwayElevatorRepositoryCustom {
} 