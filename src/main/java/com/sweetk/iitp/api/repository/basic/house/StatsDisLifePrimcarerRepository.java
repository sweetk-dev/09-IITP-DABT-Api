package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.entity.basic.house.StatsDisLifePrimcarer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisLifePrimcarerRepository extends JpaRepository<StatsDisLifePrimcarer, Integer> {
}