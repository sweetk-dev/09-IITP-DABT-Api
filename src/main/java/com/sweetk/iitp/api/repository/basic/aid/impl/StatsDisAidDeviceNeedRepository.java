package com.sweetk.iitp.api.repository.basic.aid.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.aid.StatsDisAidDeviceNeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

interface StatsDisAidDeviceNeedRepositoryCustom {
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
}

@Repository
public interface StatsDisAidDeviceNeedRepository extends JpaRepository<StatsDisAidDeviceNeedEntity, Long>, StatsDisAidDeviceNeedRepositoryCustom {
}