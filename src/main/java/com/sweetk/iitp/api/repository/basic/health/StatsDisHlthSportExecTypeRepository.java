package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthSportExecTypeEntity;
import com.sweetk.iitp.api.repository.basic.health.custom.StatsDisHlthSportExecTypeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisHlthSportExecTypeRepository extends JpaRepository<StatsDisHlthSportExecTypeEntity, Long>, StatsDisHlthSportExecTypeRepositoryCustom {
}