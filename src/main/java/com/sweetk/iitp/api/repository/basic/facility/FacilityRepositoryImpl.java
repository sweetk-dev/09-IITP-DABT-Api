package com.sweetk.iitp.api.repository.basic.facility;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.facility.impl.StatsDisFcltyWelfareUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FacilityRepositoryImpl implements FacilityRepository {
    private final StatsDisFcltyWelfareUsageRepository fcltyWelfareUsageRepos;


    @Override
    public Integer getFcltyWelfareUsageLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return fcltyWelfareUsageRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findFcltyWelfareUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return fcltyWelfareUsageRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findFcltyWelfareUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return fcltyWelfareUsageRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 