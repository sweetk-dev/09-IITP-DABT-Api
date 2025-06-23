package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.housing.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HousingRepositoryImpl implements HousingRepository {
    
    private final StatsDisLifeMaincarerRepository lifeMaincarerRepos;
    private final StatsDisLifePrimcarerRepository lifePrimcarerRepos;
    private final StatsDisLifeSuppFieldRepository lifeSuppFieldRepos;
    private final StatsDisLifeSuppNeedLvlRepository lifeSuppNeedLvlRepos;
    private final StatsDisRegNatlByAgeTypeSevGenRepository regNatlByAgeTypeSevGenRepos;
    private final StatsDisRegNatlByNewRepository regNatlByNewRepos;
    private final StatsDisRegSidoByTypeSevGenRepository regSidoByAgeTypeSevGenRepos;

    @Override
    public List<StatDataItemDB> findRegNatlByNewLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear) {
        return regNatlByNewRepos.findDataLatest(srcDataInfo, fromStatYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByNewByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regNatlByNewRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByAgeTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear) {
        return regNatlByAgeTypeSevGenRepos.findDataLatest(srcDataInfo, fromStatYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByAgeTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regNatlByAgeTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findRegSidoByTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear) {
        return regSidoByAgeTypeSevGenRepos.findDataLatest(srcDataInfo, fromStatYear);
    }

    @Override
    public List<StatDataItemDB> findRegSidoByTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regSidoByAgeTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppNeedLvlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear) {
        return lifeSuppNeedLvlRepos.findDataLatest(srcDataInfo, fromStatYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppNeedLvlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeSuppNeedLvlRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findLifeMaincarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear) {
        return lifeMaincarerRepos.findDataLatest(srcDataInfo, fromStatYear);
    }

    @Override
    public List<StatDataItemDB> findLifeMaincarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeMaincarerRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findLifePrimcarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear) {
        return lifePrimcarerRepos.findDataLatest(srcDataInfo, fromStatYear);
    }

    @Override
    public List<StatDataItemDB> findLifePrimcarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifePrimcarerRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppFieldLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromStatYear) {
        return lifeSuppFieldRepos.findDataLatest(srcDataInfo, fromStatYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppFieldByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeSuppFieldRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 