package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthExrcBestAid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisHlthExrcBestAidRepository extends JpaRepository<StatsDisHlthExrcBestAid, Integer> {
}