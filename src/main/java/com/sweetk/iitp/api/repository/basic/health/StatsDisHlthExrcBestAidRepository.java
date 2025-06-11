package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthExrcBestAidEntity;
import com.sweetk.iitp.api.repository.basic.health.custom.StatsDisHlthExrcBestAidRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisHlthExrcBestAidRepository extends JpaRepository<StatsDisHlthExrcBestAidEntity, Long>, StatsDisHlthExrcBestAidRepositoryCustom {
}