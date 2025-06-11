package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifePrimcarerEntity;
import com.sweetk.iitp.api.repository.basic.housing.custom.StatsDisLifePrimcarerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisLifePrimcarerRepository extends JpaRepository<StatsDisLifePrimcarerEntity, Integer>,
        StatsDisLifePrimcarerRepositoryCustom {
}