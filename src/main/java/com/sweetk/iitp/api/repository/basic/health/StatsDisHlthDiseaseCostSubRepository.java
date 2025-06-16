package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthDiseaseCostSubEntity;
import com.sweetk.iitp.api.repository.basic.health.impl.StatsDisHlthDiseaseCostSubRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisHlthDiseaseCostSubRepository extends JpaRepository<StatsDisHlthDiseaseCostSubEntity, Long>, StatsDisHlthDiseaseCostSubRepositoryCustom {
}