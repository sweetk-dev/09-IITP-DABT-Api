package com.sweetk.iitp.api.repository.basic.health.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;

import java.util.List;

public interface StatsDisHlthMedicalUsageRepositoryCustom {
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 