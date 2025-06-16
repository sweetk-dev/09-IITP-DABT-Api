package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifeMaincarerEntity;
import com.sweetk.iitp.api.repository.basic.housing.impl.StatsDisLifeMaincarerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisLifeMaincarerRepository extends JpaRepository<StatsDisLifeMaincarerEntity, Integer>,
        StatsDisLifeMaincarerRepositoryCustom {
}