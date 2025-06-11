package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HouseRepositoryImpl implements HouseRepository {
    private final StatsDisLifeMaincarerRepository lifeMaincarerRepos;
    private final StatsDisLifePrimcarerRepository lifePrimcarerRepos;
    private final StatsDisLifeSuppNeedLvlRepository lifeSuppNeedLvlRepos;
    private final StatsDisLifeSuppFieldRepository lifeSuppFieldRepos;
    private final StatsDisRegSidoByTypeSevGenRepository regSidoByTypeSevGenRepos;
    private final StatsDisRegNatlByNewRepository regNatlByNewRepos;
    private final StatsDisRegNatlByAgeTypeSevGenRepository regNatlByAgeTypeSevGenRepos;

    @Override
    public List<StatDataItemDB> findLifeMaincarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return lifeMaincarerRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findLifeMaincarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeMaincarerRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findLifePrimcarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return lifePrimcarerRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findLifePrimcarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifePrimcarerRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppNeedLvlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return lifeSuppNeedLvlRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppNeedLvlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeSuppNeedLvlRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppFieldLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return lifeSuppFieldRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppFieldByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeSuppFieldRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findRegSidoByTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return regSidoByTypeSevGenRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findRegSidoByTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regSidoByTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByNewLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return regNatlByNewRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByNewByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regNatlByNewRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByAgeTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return regNatlByAgeTypeSevGenRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByAgeTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regNatlByAgeTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 