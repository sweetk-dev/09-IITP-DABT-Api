package com.sweetk.iitp.api.repository.basic.housing.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegNatlByAgeTypeSevGenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

interface StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {
    Integer getDataLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);

    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    //List<StatDataItemDB> findDataLatest(com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
}

@Repository
public interface StatsDisRegNatlByAgeTypeSevGenRepository
        extends JpaRepository<StatsDisRegNatlByAgeTypeSevGenEntity, Integer>, StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {
}