package com.sweetk.iitp.api.repository.basic.housing.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegSggBySevGenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

interface StatsDisRegSggBySevGenRepositoryCustom {
    Integer getDataLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);

    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    //List<StatDataItemDB> findLatestRegNewData(com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
}

@Repository
public interface StatsDisRegSggBySevGenRepository
        extends JpaRepository<StatsDisRegSggBySevGenEntity, Integer>,
        StatsDisRegSggBySevGenRepositoryCustom {
}