package com.sweetk.iitp.api.repository.basic.emp.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.QStatsDisEmpNatlDisTypeIndustEntity;
import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlDisTypeIndustEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisEmpNatlDisTypeIndustRepositoryImpl
        extends BasicQuerySupport<StatsDisEmpNatlDisTypeIndustEntity>
        implements StatsDisEmpNatlDisTypeIndustRepositoryCustom {

    private final QStatsDisEmpNatlDisTypeIndustEntity qEntity = QStatsDisEmpNatlDisTypeIndustEntity.statsDisEmpNatlDisTypeIndustEntity;

    public StatsDisEmpNatlDisTypeIndustRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
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