package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HealthRepositoryImpl implements HealthRepository {
    private final StatsDisHlthSportExecTypeRepository hlthSportExecTypeRepos;
    private final StatsDisHlthMedicalUsageRepository hlthMedicalUsageRepos;
    private final StatsDisHlthExrcBestAidRepository hlthExrcBestAidRepos;
    private final StatsDisHlthDiseaseCostSubRepository hlthDiseaseCostSubRepos;

    @Override
    public List<StatDataItemDB> findHlthSportExecTypeLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return hlthSportExecTypeRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findHlthSportExecTypeByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthSportExecTypeRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findHlthMedicalUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return hlthMedicalUsageRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findHlthMedicalUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthMedicalUsageRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findHlthExrcBestAidLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return hlthExrcBestAidRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findHlthExrcBestAidByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthExrcBestAidRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findHlthDiseaseCostSubLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return hlthDiseaseCostSubRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findHlthDiseaseCostSubByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthDiseaseCostSubRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 