package com.sweetk.iitp.api.repository.basic.aid;

import com.sweetk.iitp.api.entity.basic.aid.StatsDisAidDeviceNeedEntity;
import com.sweetk.iitp.api.repository.basic.aid.impl.StatsDisAidDeviceNeedRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisAidDeviceNeedRepository extends JpaRepository<StatsDisAidDeviceNeedEntity, Long>, StatsDisAidDeviceNeedRepositoryCustom {
}