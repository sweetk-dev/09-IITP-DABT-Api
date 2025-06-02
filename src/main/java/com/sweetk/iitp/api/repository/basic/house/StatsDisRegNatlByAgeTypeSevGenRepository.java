package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegNatlByAgeTypeSevGen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisRegNatlByAgeTypeSevGenRepository extends JpaRepository<StatsDisRegNatlByAgeTypeSevGen, Integer> {
}