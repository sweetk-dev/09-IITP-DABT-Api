package com.sweetk.iitp.api.repository.basic.housing.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegSidoByTypeSevGenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

interface StatsDisRegSidoByTypeSevGenRepositoryCustom {
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);

    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    //List<StatDataItemDB> findLatestRegNewData(com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
}

@Repository
public interface StatsDisRegSidoByTypeSevGenRepository
        extends JpaRepository<StatsDisRegSidoByTypeSevGenEntity, Integer>,
        StatsDisRegSidoByTypeSevGenRepositoryCustom {
}