package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.StatsKosisOriginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsKosisOriginDataRepository extends JpaRepository<StatsKosisOriginData, Integer> {
}