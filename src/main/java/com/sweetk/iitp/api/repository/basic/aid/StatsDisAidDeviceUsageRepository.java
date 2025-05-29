package com.sweetk.iitp.api.repository.basic.aid;

import com.sweetk.iitp.api.entity.basic.aid.StatsDisAidDeviceUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisAidDeviceUsageRepository extends JpaRepository<StatsDisAidDeviceUsage, Integer> {
}