package com.sweetk.iitp.api.repository.basic.aid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisAidDeviceUsageRepository extends JpaRepository<StatsDisAidDeviceUsageEntity, Integer> {
}