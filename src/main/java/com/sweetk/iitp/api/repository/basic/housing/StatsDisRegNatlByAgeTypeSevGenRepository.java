package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegNatlByAgeTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.housing.custom.StatsDisRegNatlByAgeTypeSevGenRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisRegNatlByAgeTypeSevGenRepository
        extends JpaRepository<StatsDisRegNatlByAgeTypeSevGenEntity, Integer>, StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {
}