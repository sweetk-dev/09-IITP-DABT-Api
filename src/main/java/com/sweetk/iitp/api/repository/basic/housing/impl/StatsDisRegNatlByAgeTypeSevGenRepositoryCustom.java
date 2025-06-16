package com.sweetk.iitp.api.repository.basic.housing.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;

import java.util.List;

public interface StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);

    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    //List<StatDataItemDB> findDataLatest(com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
} 