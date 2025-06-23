package com.sweetk.iitp.api.repository.basic.health.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthExrcBestAidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


interface StatsDisHlthExrcBestAidRepositoryCustom {
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
}

@Repository
public interface StatsDisHlthExrcBestAidRepository extends JpaRepository<StatsDisHlthExrcBestAidEntity, Long>, StatsDisHlthExrcBestAidRepositoryCustom {
}