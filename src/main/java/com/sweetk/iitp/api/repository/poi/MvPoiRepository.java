package com.sweetk.iitp.api.repository.poi;

import com.sweetk.iitp.api.entity.poi.MvPoiEntity;
import com.sweetk.iitp.api.repository.poi.impl.MvPoiRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MvPoiRepository extends JpaRepository<MvPoiEntity, Long>, MvPoiRepositoryCustom {
   }
