package com.sweetk.iitp.api.repository.basic.facility;

import com.sweetk.iitp.api.entity.basic.facility.StatsDisFcltyWelfareUsageEntity;
import com.sweetk.iitp.api.repository.basic.facility.custom.StatsDisFcltyWelfareUsageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisFcltyWelfareUsageRepository extends JpaRepository<StatsDisFcltyWelfareUsageEntity, Long>, StatsDisFcltyWelfareUsageRepositoryCustom {
}