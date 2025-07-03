package com.sweetk.iitp.api.repository.basic.facility;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository {
    // StatsDisFcltyWelfareUsage
    Integer getFcltyWelfareUsageLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findFcltyWelfareUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);

    List<StatDataItemDB> findFcltyWelfareUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
}