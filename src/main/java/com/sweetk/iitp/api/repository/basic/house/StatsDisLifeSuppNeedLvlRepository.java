package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifeSuppNeedLvl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisLifeSuppNeedLvlRepository extends JpaRepository<StatsDisLifeSuppNeedLvl, Integer> {
}