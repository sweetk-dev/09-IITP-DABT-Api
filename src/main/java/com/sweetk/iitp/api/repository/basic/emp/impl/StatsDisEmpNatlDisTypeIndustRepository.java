package com.sweetk.iitp.api.repository.basic.emp.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlDisTypeIndustEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


interface StatsDisEmpNatlDisTypeIndustRepositoryCustom {
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
}

@Repository
public interface StatsDisEmpNatlDisTypeIndustRepository extends JpaRepository<StatsDisEmpNatlDisTypeIndustEntity, Long>, StatsDisEmpNatlDisTypeIndustRepositoryCustom {
}