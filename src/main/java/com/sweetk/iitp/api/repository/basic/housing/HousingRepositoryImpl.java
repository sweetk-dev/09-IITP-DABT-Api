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



    //RegNatlByNew
    @Override
    public Integer getRegNatlByNewLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return regNatlByNewRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByNewLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return regNatlByNewRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByNewByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regNatlByNewRepos.findDataByYear(srcDataInfo, targetYear);
    }

    //RegNatlByAgeTypeSevGen
    @Override
    public Integer getRegNatlByAgeTypeSevGenLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return regNatlByAgeTypeSevGenRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByAgeTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return regNatlByAgeTypeSevGenRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findRegNatlByAgeTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regNatlByAgeTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //RegSidoByTypeSevGen
    @Override
    public Integer getRegSidoByTypeSevGenLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return regSidoByAgeTypeSevGenRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findRegSidoByTypeSevGenLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return regSidoByAgeTypeSevGenRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findRegSidoByTypeSevGenByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return regSidoByAgeTypeSevGenRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //LifeSuppNeedLvl
    @Override
    public Integer getLifeSuppNeedLvlLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifeSuppNeedLvlRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppNeedLvlLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifeSuppNeedLvlRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppNeedLvlByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeSuppNeedLvlRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //LifeMaincarer
    @Override
    public Integer getLifeMaincarerLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifeMaincarerRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifeMaincarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifeMaincarerRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifeMaincarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeMaincarerRepos.findDataByYear(srcDataInfo, targetYear);
    }

    //LifePrimcarer
    @Override
    public Integer getLifePrimcarerLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifePrimcarerRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifePrimcarerLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifePrimcarerRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifePrimcarerByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifePrimcarerRepos.findDataByYear(srcDataInfo, targetYear);
    }


    //LifeSuppField
    @Override
    public Integer getLifeSuppFieldLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifeSuppFieldRepos.getDataLatestCount(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppFieldLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return lifeSuppFieldRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findLifeSuppFieldByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return lifeSuppFieldRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 