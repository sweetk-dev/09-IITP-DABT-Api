package com.sweetk.iitp.api.repository.basic.health.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.health.QStatsDisHlthDiseaseCostSubEntity;
import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthDiseaseCostSubEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisHlthDiseaseCostSubRepositoryImpl
        extends BasicQuerySupport<StatsDisHlthDiseaseCostSubEntity>
        implements StatsDisHlthDiseaseCostSubRepositoryCustom {

    private final QStatsDisHlthDiseaseCostSubEntity qEntity = QStatsDisHlthDiseaseCostSubEntity.statsDisHlthDiseaseCostSubEntity;

    public StatsDisHlthDiseaseCostSubRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }


    @Override
    public Integer getDataLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear){
        return getLatestStatsCount(qEntity, srcDataInfo, fromYear, toYear);
    }


    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 