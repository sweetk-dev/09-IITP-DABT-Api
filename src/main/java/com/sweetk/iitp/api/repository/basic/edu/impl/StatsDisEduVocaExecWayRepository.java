package com.sweetk.iitp.api.repository.basic.edu.impl;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.edu.StatsDisEduVocaExecWayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

interface StatsDisEduVocaExecWayRepositoryCustom {
    List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
}

@Repository
public interface StatsDisEduVocaExecWayRepository extends JpaRepository<StatsDisEduVocaExecWayEntity, Long>, StatsDisEduVocaExecWayRepositoryCustom {
}