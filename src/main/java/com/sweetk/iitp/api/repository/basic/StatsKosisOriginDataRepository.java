package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.StatsKosisOriginDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsKosisOriginDataRepository extends JpaRepository<StatsKosisOriginDataEntity, Integer> {
}