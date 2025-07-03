package com.sweetk.iitp.api.repository.basic.emp.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlDisTypeSevEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

interface StatsDisEmpNatlDisTypeSevRepositoryCustom {
    Integer getDataLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
}

@Repository
public interface StatsDisEmpNatlDisTypeSevRepository extends JpaRepository<StatsDisEmpNatlDisTypeSevEntity, Long>, StatsDisEmpNatlDisTypeSevRepositoryCustom {
}