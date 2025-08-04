package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.entity.poi.PoiPublicToiletInfoEntity;
import com.sweetk.iitp.api.repository.poi.impl.PoiPublicToiletInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiPublicToiletInfoRepository extends JpaRepository<PoiPublicToiletInfoEntity, Integer>, PoiPublicToiletInfoRepositoryCustom {
} 