package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.health.impl.StatsDisHlthDiseaseCostSubRepository;
import com.sweetk.iitp.api.repository.basic.health.impl.StatsDisHlthExrcBestAidRepository;
import com.sweetk.iitp.api.repository.basic.health.impl.StatsDisHlthMedicalUsageRepository;
import com.sweetk.iitp.api.repository.basic.health.impl.StatsDisHlthSportExecTypeRepository;
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


    //HlthSportExecType
    @Override
    public Integer getHlthSportExecTypeLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthSportExecTypeRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthSportExecTypeLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthSportExecTypeRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthSportExecTypeByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthSportExecTypeRepos.findDataByYear(srcDataInfo, targetYear);
    }

    //HlthMedicalUsage
    @Override
    public Integer getHlthMedicalUsageLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthMedicalUsageRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthMedicalUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthMedicalUsageRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthMedicalUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthMedicalUsageRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //HlthExrcBestAid
    @Override
    public Integer getHlthExrcBestAidLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthExrcBestAidRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthExrcBestAidLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthExrcBestAidRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthExrcBestAidByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthExrcBestAidRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //HlthDiseaseCostSub
    @Override
    public Integer getHlthDiseaseCostSubLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthDiseaseCostSubRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthDiseaseCostSubLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return hlthDiseaseCostSubRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findHlthDiseaseCostSubByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return hlthDiseaseCostSubRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 