package com.sweetk.iitp.api.repository.basic.aid;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.aid.impl.StatsDisAidDeviceNeedRepository;
import com.sweetk.iitp.api.repository.basic.aid.impl.StatsDisAidDeviceUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AidRepositoryImpl implements AidRepository {
    private final StatsDisAidDeviceNeedRepository aidDeviceNeedRepos;
    private final StatsDisAidDeviceUsageRepository aidDeviceUsageRepos;

    @Override
    public List<StatDataItemDB> findAidDeviceNeedLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return aidDeviceNeedRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findAidDeviceNeedByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return aidDeviceNeedRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findAidDeviceUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return aidDeviceUsageRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findAidDeviceUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return aidDeviceUsageRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 